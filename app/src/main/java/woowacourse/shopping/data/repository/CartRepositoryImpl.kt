package woowacourse.shopping.data.repository

import woowacourse.shopping.data.source.CartDataSource
import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.repository.CartRepository

class CartRepositoryImpl(
    private val cartDataSource: CartDataSource,
) : CartRepository {
    private val cartItems
        get() = cartDataSource.items

    private val totalPage
        get() = ((cartItems.size + PAGE_SIZE - 1) / PAGE_SIZE).coerceAtLeast(1)

    override val cartItemCount: Int
        get() = cartItems.size

    override fun isLastPage(page: Int) = page == totalPage

    override fun addItem(
        product: Product,
        amount: Int,
    ) {
        cartDataSource.add(CartItem(product, amount))
    }

    override fun deleteItem(productId: String) {
        cartDataSource.deleteItem(productId)
    }

    override suspend fun getCartItemByPage(page: Int): List<CartItem> {
        require(page in 1..totalPage) { "페이지가 올바르지 않습니다." }

        val startIndex = (page - 1) * PAGE_SIZE
        val endIndex = minOf(startIndex + PAGE_SIZE, cartItems.size)

        return cartItems.subList(startIndex, endIndex)
    }

    override fun getItemCount(productId: String): Int =
        cartItems
            .firstOrNull { it.product.id == productId }
            ?.quantity
            ?: 0

    override fun plusItemCount(product: Product) {
        val item = cartItems.firstOrNull { it.product == product }

        if (item == null) {
            addItem(product = product, amount = 1)
            return
        }

        cartDataSource.updateItem(item.addQuantity(1))
    }

    override fun minusItemCount(productId: String) {
        val item = cartItems.firstOrNull { it.product.id == productId }

        requireNotNull(item) { "카트에 아이템이 존재하지 않습니다." }

        if (item.quantity == 1) {
            cartDataSource.deleteItem(productId = productId)
            return
        }

        cartDataSource.updateItem(cartItem = item.minusQuantity(1))
    }

    companion object {
        private const val PAGE_SIZE = 5
    }
}
