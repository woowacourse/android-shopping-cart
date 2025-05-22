package woowacourse.shopping.view.cart.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.view.ShoppingApp.Companion.cartRepository
import woowacourse.shopping.view.ShoppingApp.Companion.productRepository

class CartViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            return CartViewModel(cartRepository, productRepository) as T
        }
        throw IllegalArgumentException()
    }
}
