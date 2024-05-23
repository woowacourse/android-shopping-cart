package woowacourse.shopping.ui.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.model.data.ProductDao
import woowacourse.shopping.model.data.RecentProductDao

class ProductDetailViewModelFactory(
    private val productDao: ProductDao,
    private val recentProductDao: RecentProductDao,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductDetailViewModel(productDao, recentProductDao) as T
    }
}
