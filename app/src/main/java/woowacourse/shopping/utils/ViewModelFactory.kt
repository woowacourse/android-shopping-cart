package woowacourse.shopping.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.domain.product.ProductRepository
import woowacourse.shopping.ui.viewmodel.ProductListViewModel

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
