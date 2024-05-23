package woowacourse.shopping.ui.products.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.model.data.CartDao
import woowacourse.shopping.model.data.ProductDao
import woowacourse.shopping.model.data.RecentProductDao

class ProductContentsViewModelFactory(
    private val productDao: ProductDao,
    private val recentProductDao: RecentProductDao,
    private val cartDao: CartDao,
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductContentsViewModel(productDao, recentProductDao, cartDao) as T
    }
}
