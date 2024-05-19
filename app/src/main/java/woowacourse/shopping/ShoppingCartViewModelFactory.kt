package woowacourse.shopping

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.cart.ShoppingCartViewModel
import woowacourse.shopping.repository.ShoppingCartItemRepository

class ShoppingCartViewModelFactory(private val repository: ShoppingCartItemRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShoppingCartViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ShoppingCartViewModel(repository) as T
        }
        throw IllegalArgumentException("this is known viewmodel class")
    }
}
