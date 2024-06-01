package woowacourse.shopping.view.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.domain.repository.CartRepository

class ShoppingCartViewModelFactory(
    private val cartRepository: CartRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ShoppingCartViewModel::class.java)) {
            ShoppingCartViewModel(cartRepository) as T
        } else {
            throw IllegalArgumentException(UNKNOWN_VIEWMODEL)
        }
    }

    companion object {
        private const val UNKNOWN_VIEWMODEL = "Unknown ViewModel class"
    }
}
