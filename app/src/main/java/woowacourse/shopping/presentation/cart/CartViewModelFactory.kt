package woowacourse.shopping.presentation.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.di.RepositoryModule

class CartViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val cartRepository = RepositoryModule.provideCartRepository()
        return CartViewModel(cartRepository) as T
    }
}
