package woowacourse.shopping.view.main.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.HistoryRepository
import woowacourse.shopping.domain.repository.ProductRepository

class MainViewModelFactory(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val historyRepository: HistoryRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(productRepository, cartRepository, historyRepository) as T
        }
        throw IllegalArgumentException()
    }
}
