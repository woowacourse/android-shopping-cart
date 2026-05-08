package woowacourse.shopping.repository.cart

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import woowacourse.shopping.data.local.dao.CartDao
import woowacourse.shopping.data.local.entity.CartItemEntity
import woowacourse.shopping.domain.Price
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.ProductWithQuantity
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class RoomCartRepository(
    private val cartDao: CartDao,
) : CartRepository {
    override suspend fun getTotalProductQuantity(): Int = cartDao.getTotalQuantity()

    override suspend fun getProductQuantity(productId: Uuid): Int =
        cartDao.getByProductId(productId = productId.toString())?.quantity ?: 0

    override fun getCartProducts(): Flow<List<ProductWithQuantity>> =
        cartDao.getCartProducts().map { rows ->
            rows.map { row ->
                ProductWithQuantity(
                    product = Product(
                        productId = Uuid.parse(row.productId),
                        imageUrl = row.imageUrl,
                        productName = row.productName,
                        price = Price(row.price),
                    ),
                    quantity = row.quantity,
                )
            }
        }

    override suspend fun addProduct(product: Product, quantityToAdd: Int) {
        val productId = product.productId.toString()
        val existing = cartDao.getByProductId(productId)

        if (existing == null) {
            cartDao.insert(CartItemEntity(productId = productId, quantity = quantityToAdd))
        } else {
            cartDao.updateQuantity(productId, existing.quantity + quantityToAdd)
        }
    }


    override suspend fun deleteProduct(productId: Uuid) {
        return cartDao.deleteByProductId(productId = productId.toString())
    }

    override suspend fun decreaseProduct(productId: Uuid, quantityToRemove: Int) {
        val cartProductId = productId.toString()
        val existing = cartDao.getByProductId(cartProductId) ?: return
        val updatedQuantity = existing.quantity - quantityToRemove

        if (updatedQuantity > 0) {
            cartDao.updateQuantity(cartProductId, updatedQuantity)
        } else {
            cartDao.deleteByProductId(cartProductId)
        }
    }

}
