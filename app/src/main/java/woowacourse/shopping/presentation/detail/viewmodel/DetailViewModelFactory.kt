package woowacourse.shopping.presentation.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductHistoryRepository
import woowacourse.shopping.domain.repository.ProductRepository

class DetailViewModelFactory(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val productHistoryRepository: ProductHistoryRepository,
    private val productId: Long,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            DetailViewModel(
                productRepository,
                cartRepository,
                productHistoryRepository,
                productId,
            ) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}
