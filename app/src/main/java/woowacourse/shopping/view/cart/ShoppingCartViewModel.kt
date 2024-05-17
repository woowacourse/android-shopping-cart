package woowacourse.shopping.view.cart

import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.view.cart.model.ShoppingCart
import kotlin.concurrent.thread

class ShoppingCartViewModel(
    private val repository: ProductRepository,
) : ViewModel() {
    var shoppingCart = ShoppingCart()

    fun deleteShoppingCartItem(itemId: Long) {
        thread {
            repository.deleteCartItem(itemId)
        }.join()
        shoppingCart.deleteProduct(itemId)
    }

    fun loadPagingCartItem(pagingSize: Int) {
        var pagingData = emptyList<CartItem>()
        thread {
            val itemSize = shoppingCart.cartItems.value?.size ?: ShoppingCartFragment.DEFAULT_ITEM_SIZE
            pagingData = repository.loadPagingCartItems(itemSize, pagingSize)
        }.join()
        if (pagingData.isNotEmpty()) {
            shoppingCart.addProducts(pagingData)
        }
    }
}
