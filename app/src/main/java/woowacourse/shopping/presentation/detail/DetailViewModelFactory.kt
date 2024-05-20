package woowacourse.shopping.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository

class DetailViewModelFactory(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val productId: Long,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras,
    ): T {
        return if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            DetailViewModel(
                productRepository,
                cartRepository,
                productId,
                extras.createSavedStateHandle()
            ) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}
