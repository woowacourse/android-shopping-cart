package woowacourse.shopping.view.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.domain.repository.ProductRepository

class DetailViewModelFactory(private val repository: ProductRepository, private val productId: Long) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ProductDetailViewModel::class.java)) {
            ProductDetailViewModel(repository, productId) as T
        } else {
            throw IllegalArgumentException(UNKNOWN_VIEWMODEL)
        }
    }

    companion object {
        private const val UNKNOWN_VIEWMODEL = "Unknown ViewModel class"
    }
}
