package woowacourse.shopping.data.source

import woowacourse.shopping.data.source.local.cart.CartItemDao
import woowacourse.shopping.data.source.local.cart.CartItemEntity

class CartDataSourceImpl(
    private val dao: CartItemDao,
) : CartDataSource {
    override suspend fun getCartItems(
        offset: Int,
        count: Int,
    ): List<CartItemEntity> = dao.getCartItems(offset = offset, count = count)

    override suspend fun add(cartItem: CartItemEntity) {
        dao.insert(cartItem = cartItem)
    }

    override suspend fun deleteItem(productId: String) {
        dao.delete(productId = productId)
    }

    override suspend fun updateItem(cartItem: CartItemEntity) {
        dao.update(cartItem = cartItem)
    }

    override suspend fun getCartItemById(productId: String): CartItemEntity? = dao.getCartItemById(productId = productId)

    override suspend fun getTotalCount(): Int = dao.getTotalCount()
}
