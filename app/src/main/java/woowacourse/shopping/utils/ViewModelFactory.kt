package woowacourse.shopping.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.ui.fashionlist.ProductListViewModel

object ViewModelFactory {
    fun createProductViewModelFactory(productRepository: ProductRepository): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ProductListViewModel(productRepository) as T
            }
        }
    }
}
