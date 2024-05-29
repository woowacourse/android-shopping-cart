package woowacourse.shopping.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.model.data.OrderDao
import woowacourse.shopping.model.data.ProductDao
import woowacourse.shopping.model.data.RecentProductDao

class ProductDetailViewModelFactory(
    private val productDao: ProductDao,
    private val orderDao: OrderDao,
    private val recentProductDao: RecentProductDao,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductDetailViewModel(productDao, orderDao, recentProductDao) as T
    }
}
