package woowacourse.shopping.view.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.db.cart.CartRepository
import woowacourse.shopping.data.db.product.ProductRepository
import woowacourse.shopping.data.db.recent.RecentProductRepository

class DetailViewModelFactory(
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository,
    private val recentProductRepository: RecentProductRepository,
    private val productId: Long,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailViewModel(
                cartRepository = cartRepository,
                productRepository = productRepository,
                recentProductRepository = recentProductRepository,
                productId = productId,
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
