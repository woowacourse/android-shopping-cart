package woowacourse.shopping.presentation.productdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.di.RepositoryModule

class ProductDetailViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val cartRepository = RepositoryModule.provideCartRepository()
        val productRepository = RepositoryModule.provideProductRepository()
        val recentProductRepository = RepositoryModule.provideRecentProductRepository()
        return ProductDetailViewModel(
            cartRepository,
            productRepository,
            recentProductRepository,
        ) as T
    }
}
