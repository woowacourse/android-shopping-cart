package woowacourse.shopping.repository.cart

import androidx.room.Transaction
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
                    product =
                        Product(
                            productId = Uuid.parse(row.productId),
                            imageUrl = row.imageUrl,
                            productName = row.productName,
                            price = Price(row.price),
                        ),
                    quantity = row.quantity,
                )
            }
        }

    override suspend fun addProduct(
        product: Product,
        quantityToAdd: Int,
    ) {
        require(quantityToAdd > 0) { "추가 수량은 1 이상이어야 합니다." }
        val productId = product.productId.toString()
        val existing = cartDao.getByProductId(productId)
        val newQuantity = (existing?.quantity ?: 0) + quantityToAdd
        cartDao.upsert(CartItemEntity(productId = productId, quantity = newQuantity))
    }

    override suspend fun deleteProduct(productId: Uuid) =
        cartDao.deleteByProductId(productId = productId.toString())

    @Transaction
    override suspend fun decreaseProduct(
        productId: Uuid,
        quantityToRemove: Int,
    ) {
        require(quantityToRemove > 0) { "감소 수량은 1 이상이어야 합니다." }
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
