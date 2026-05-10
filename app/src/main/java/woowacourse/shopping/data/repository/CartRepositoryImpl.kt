package woowacourse.shopping.data.repository

import woowacourse.shopping.data.local.dao.CartDao
import woowacourse.shopping.data.local.mapper.toCartItemEntity
import woowacourse.shopping.data.local.mapper.toDomain
import woowacourse.shopping.domain.model.cart.Cart
import woowacourse.shopping.domain.model.product.Product
import woowacourse.shopping.domain.repository.CartRepository

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

    override suspend fun increaseQuantity(
        product: Product,
        quantity: Int,
    ) {
        val savedItem = cartDao.findByProductId(product.productId)

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

    override suspend fun decreaseQuantity(productId: Int) {
        val savedItem = cartDao.findByProductId(productId) ?: return

        if (savedItem.quantity == 1) {
            cartDao.deleteByProductId(productId)
            return
        }

        cartDao.save(
            savedItem.copy(quantity = savedItem.quantity - 1),
        )
    }

    override suspend fun deleteProduct(productId: Int) {
        cartDao.deleteByProductId(productId)
    }
}
