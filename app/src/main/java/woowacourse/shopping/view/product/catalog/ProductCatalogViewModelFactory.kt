package woowacourse.shopping.view.product.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.product.LocalProductRepository

class ProductCatalogViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductCatalogViewModel::class.java)) {
            val repository = LocalProductRepository()
            return ProductCatalogViewModel(repository) as T
        }
        throw IllegalArgumentException()
    }
}
