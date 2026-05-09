package woowacourse.shopping.data.repository

import woowacourse.shopping.data.local.dao.CartDao
import woowacourse.shopping.data.mapper.toCartItemEntity
import woowacourse.shopping.data.mapper.toDomain
import woowacourse.shopping.domain.model.cart.Cart
import woowacourse.shopping.domain.model.product.Product
import woowacourse.shopping.domain.repository.CartRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class CartRepositoryImpl(
    private var cartDao: CartDao,
) : CartRepository {
    override suspend fun getItems(): Cart = Cart(cartDao.findAll().map { it.toDomain() })

    override suspend fun getPagingItems(
        page: Int,
        pageSize: Int,
    ): Cart {
        if (page < 0 || pageSize <= 0) return Cart()

        val offset = page * pageSize

        return Cart(
            cartItems =
                cartDao
                    .findPagingItems(
                        limit = pageSize,
                        offset = offset,
                    ).map { it.toDomain() },
        )
    }

    override suspend fun getTotalItemCount(): Int = cartDao.countItems()

    override suspend fun getTotalQuantity(): Int = cartDao.sumQuantity()

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun increaseQuantity(
        product: Product,
        quantity: Int,
    ) {
        val savedItem = cartDao.findByProductId(product.productId.toString())

        val newQuantity =
            if (savedItem == null) {
                quantity
            } else {
                savedItem.quantity + quantity
            }
        cartDao.save(
            product.toCartItemEntity(quantity = newQuantity),
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun decreaseQuantity(productId: Uuid) {
        val savedItem = cartDao.findByProductId(productId.toString()) ?: return

        if (savedItem.quantity == 1) {
            cartDao.deleteByProductId(productId.toString())
            return
        }

        cartDao.save(
            savedItem.copy(quantity = savedItem.quantity - 1),
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun deleteProduct(productId: Uuid) {
        cartDao.deleteByProductId(productId.toString())
    }
}
