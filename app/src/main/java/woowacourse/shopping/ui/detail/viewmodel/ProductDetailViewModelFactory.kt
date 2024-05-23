package woowacourse.shopping.ui.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.model.data.ProductDao
import woowacourse.shopping.model.db.recentproduct.RecentProductRepository

class ProductDetailViewModelFactory(
    private val productDao: ProductDao,
    private val recentProductRepository: RecentProductRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductDetailViewModel(productDao, recentProductRepository) as T
    }
}
