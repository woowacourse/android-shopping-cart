package woowacourse.shopping.productList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.repository.ShoppingProductsRepository

class ProductListViewModelFactory(private val productsRepository: ShoppingProductsRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductListViewModel(productsRepository) as T
        }
        throw IllegalArgumentException("this is known viewmodel class")
    }
}
