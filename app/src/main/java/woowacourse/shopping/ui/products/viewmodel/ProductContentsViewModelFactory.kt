package woowacourse.shopping.ui.products.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.model.data.ProductDao
import woowacourse.shopping.model.db.CartDao
import woowacourse.shopping.model.db.recentproduct.RecentProductRepository

class ProductContentsViewModelFactory(
    private val productDao: ProductDao,
    private val recentProductRepository: RecentProductRepository,
    private val cartDao: CartDao,
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductContentsViewModel(productDao, recentProductRepository, cartDao) as T
    }
}
