package woowacourse.shopping.view.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.domain.repository.CartProductRepository

class ShoppingCartViewModelFactory(
    private val cartProductRepository: CartProductRepository,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShoppingCartViewModel::class.java)) {
            return ShoppingCartViewModel(cartProductRepository) as T
        }
        throw IllegalArgumentException()
    }
}
