package woowacourse.shopping.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductHistoryRepository
import woowacourse.shopping.domain.repository.ProductRepository

class HomeViewModelFactory(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val productHistoryRepository: ProductHistoryRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            HomeViewModel(productRepository, cartRepository, productHistoryRepository) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}
