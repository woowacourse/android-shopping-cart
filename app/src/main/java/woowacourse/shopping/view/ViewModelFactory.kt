package woowacourse.shopping.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.view.cart.ShoppingCartViewModel

class ViewModelFactory(private val repository: ProductRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(ShoppingCartViewModel::class.java)) {
            return ShoppingCartViewModel(repository) as T
        }
        throw IllegalArgumentException(UNKNOWN_VIEWMODEL)
    }

    companion object {
        private const val UNKNOWN_VIEWMODEL = "Unknown ViewModel class"
    }
}
