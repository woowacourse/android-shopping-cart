package woowacourse.shopping.data.repository

import woowacourse.shopping.data.source.CartDataSource
import woowacourse.shopping.data.source.CartDataSourceImpl
import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.repository.CartRepository

class CartRepositoryImpl(
    private val cartDataSource: CartDataSource = CartDataSourceImpl,
) : CartRepository {
    private val cartItems
        get() = cartDataSource.items

    private val totalPage
        get() = ((cartItems.size + PAGE_SIZE - 1) / PAGE_SIZE).coerceAtLeast(1)

    override fun isLastPage(page: Int) = page == totalPage

    override fun addItem(
        product: Product,
        amount: Int,
    ) {
        cartDataSource.add(CartItem(product, amount))
    }

    override fun deleteItem(id: String) {
        cartDataSource.deleteItem(id)
    }

    override suspend fun getCartItemByPage(page: Int): List<CartItem> {
        require(page in 1..totalPage) { "-거절(사유: ${page}pg가 말이 되는가)-" }

        val startIndex = (page - 1) * PAGE_SIZE
        val endIndex = minOf(startIndex + PAGE_SIZE, cartItems.size)

        return cartItems.subList(startIndex, endIndex)
    }

    companion object {
        private const val PAGE_SIZE = 5
    }
}
