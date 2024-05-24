package woowacourse.shopping.ui.products.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.product.ProductDao
import woowacourse.shopping.data.recentproduct.RecentProductRepository

class ProductContentsViewModelFactory(
    private val productDao: ProductDao,
    private val recentProductRepository: RecentProductRepository,
    private val cartRepository: CartRepository,
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductContentsViewModel(productDao, recentProductRepository, cartRepository) as T
    }
}
