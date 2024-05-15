package woowacourse.shopping.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.domain.repository.ShoppingItemsRepository

class DetailViewModelFactory(
    private val repository: ShoppingItemsRepository,
    private val productId: Long,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailViewModel(repository = repository, productId = productId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
