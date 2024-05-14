package woowacourse.shopping.data.repository

import woowacourse.shopping.data.datasource.CartDataSource
import woowacourse.shopping.data.model.CartItem
import woowacourse.shopping.domain.repository.CartRepository

class CartRepositoryImpl(
    private val cartDataSource: CartDataSource,
) : CartRepository {
    override fun fetchCartItems(page: Int): List<CartItem> {
        return cartDataSource.getCartItems(page, PAGE_SIZE)
    }

    override fun addCartItem(
        productId: Long,
        quantity: Int,
    ): Long {
        return cartDataSource.addCartItem(productId, quantity)
    }

    companion object {
        private const val PAGE_SIZE = 5
    }
}
