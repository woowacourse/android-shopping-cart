package woowacourse.shopping

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.productList.ProductListViewModel2
import woowacourse.shopping.repository.ShoppingProductsRepository

class ProductListViewModelFactory(private val productsRepository: ShoppingProductsRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductListViewModel2::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductListViewModel2(productsRepository) as T
        }
        throw IllegalArgumentException("this is known viewmodel class")
    }
}
