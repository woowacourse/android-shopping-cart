package woowacourse.shopping.presentation.productdetail

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.di.RepositoryModule

class ProductDetailViewModelFactory(
    private val context: Context,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val cartRepository = RepositoryModule.provideCartRepository(context)
        val productRepository = RepositoryModule.provideProductRepository(context)
        val recentProductRepository = RepositoryModule.provideRecentProductRepository(context)
        return ProductDetailViewModel(
            cartRepository,
            productRepository,
            recentProductRepository,
        ) as T
    }
}
