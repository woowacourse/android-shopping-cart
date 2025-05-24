package woowacourse.shopping.presentation.cart

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.di.RepositoryModule

class CartViewModelFactory(
    private val context: Context,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val cartRepository = RepositoryModule.provideCartRepository(context)
        val productRepository = RepositoryModule.provideProductRepository(context)
        return CartViewModel(cartRepository, productRepository) as T
    }
}
