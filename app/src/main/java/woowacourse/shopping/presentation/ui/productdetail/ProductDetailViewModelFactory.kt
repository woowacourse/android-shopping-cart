package woowacourse.shopping.presentation.ui.productdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import woowacourse.shopping.domain.repository.OrderRepository
import woowacourse.shopping.domain.repository.ProductBrowsingHistoryRepository
import woowacourse.shopping.domain.repository.ProductListRepository

class ProductDetailViewModelFactory(
    private val productListRepository: ProductListRepository,
    private val orderRepository: OrderRepository,
    private val historyRepository: ProductBrowsingHistoryRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras,
    ): T {
        return if (modelClass.isAssignableFrom(ProductDetailViewModel::class.java)) {
            ProductDetailViewModel(
                extras.createSavedStateHandle(),
                productListRepository,
                orderRepository,
                historyRepository,
            ) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}
