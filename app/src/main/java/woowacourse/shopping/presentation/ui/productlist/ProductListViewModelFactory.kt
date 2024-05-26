package woowacourse.shopping.presentation.ui.productlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.domain.repository.HistoryRepository
import woowacourse.shopping.domain.repository.OrderRepository
import woowacourse.shopping.domain.repository.ProductListRepository

class ProductListViewModelFactory(
    private val productListRepository: ProductListRepository,
    private val orderRepository: OrderRepository,
    private val historyRepository: HistoryRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ProductListViewModel::class.java)) {
            ProductListViewModel(
                productListRepository,
                orderRepository,
                historyRepository,
            ) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}
