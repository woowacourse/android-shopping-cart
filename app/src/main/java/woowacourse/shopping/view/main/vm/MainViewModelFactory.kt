package woowacourse.shopping.view.main.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.HistoryRepository
import woowacourse.shopping.view.loader.HistoryLoader
import woowacourse.shopping.view.loader.ProductWithCartLoader

class MainViewModelFactory(
    private val cartRepository: CartRepository,
    private val historyRepository: HistoryRepository,
    private val productWithCartLoader: ProductWithCartLoader,
    private val historyLoader: HistoryLoader,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(
                cartRepository,
                historyRepository,
                productWithCartLoader,
                historyLoader,
            ) as T
        }
        throw IllegalArgumentException()
    }
}
