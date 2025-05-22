package woowacourse.shopping.presentation.product

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.di.RepositoryModule

class ProductViewModelFactory(
    private val context: Context,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val cartRepository = RepositoryModule.provideCartRepository(context)
        val productRepository = RepositoryModule.provideProductRepository(context)
        return ProductViewModel(cartRepository, productRepository) as T
    }
}
