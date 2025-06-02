package woowacourse.shopping.ui.productdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.local.repository.CartRepository
import woowacourse.shopping.data.local.repository.HistoryRepository
import woowacourse.shopping.data.local.repository.ProductRepository
import woowacourse.shopping.domain.product.Product

class DetailViewModelFactory(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val historyRepository: HistoryRepository,
    private val product: Product,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(productRepository, cartRepository, historyRepository, product) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
