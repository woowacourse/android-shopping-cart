package woowacourse.shopping.view.cart

import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import woowacourse.shopping.view.cart.model.ShoppingCart
import kotlin.concurrent.thread

class ShoppingCartViewModel(
    private val shoppingCartRepository: ShoppingCartRepository,
) : ViewModel() {
    var shoppingCart = ShoppingCart()
    var currentPage = MIN_PAGE_COUNT
    val totalItemSize: Int get() = shoppingCart.cartItems.value?.size ?: DEFAULT_ITEM_SIZE

    fun deleteShoppingCartItem(itemId: Long) {
        thread {
            shoppingCartRepository.deleteCartItem(itemId)
        }.join()
        shoppingCart.deleteProduct(itemId)
    }

    fun loadPagingCartItem(pagingSize: Int) {
        var pagingData = emptyList<CartItem>()
        thread {
            val itemSize = shoppingCart.cartItems.value?.size ?: DEFAULT_ITEM_SIZE
            pagingData = shoppingCartRepository.loadPagingCartItems(itemSize, pagingSize)
        }.join()
        if (pagingData.isNotEmpty()) {
            shoppingCart.addProducts(pagingData)
        }
    }

    companion object {
        private const val DEFAULT_ITEM_SIZE = 0
        const val MIN_PAGE_COUNT = 1
    }

}
