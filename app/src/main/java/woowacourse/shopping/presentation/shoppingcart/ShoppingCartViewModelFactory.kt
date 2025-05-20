package woowacourse.shopping.presentation.shoppingcart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.shoppingcart.repository.ShoppingCartRepository

class ShoppingCartViewModelFactory(private val repository: ShoppingCartRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShoppingCartViewModel::class.java)) {
            return ShoppingCartViewModel(repository) as T
        }
        throw IllegalArgumentException(VIEWMODEL_ERROR)
    }

    companion object {
        private const val VIEWMODEL_ERROR = "[ERROR] Unknown ViewModel Class"
    }
}
