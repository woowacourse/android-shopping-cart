package woowacourse.shopping.view.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.ProductRepository
import woowacourse.shopping.data.ProductRepositoryImpl
import woowacourse.shopping.domain.Product

class ProductCatalogViewModel(
    private val repository: ProductRepository,
) : ViewModel() {
    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    init {
        loadProducts()
    }

    private fun loadProducts() {
        _products.value = repository.getAll()
    }

    companion object {
        fun provideFactory(repository: ProductRepository = ProductRepositoryImpl()): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(ProductCatalogViewModel::class.java)) {
                        return ProductCatalogViewModel(repository) as T
                    }
                    throw IllegalArgumentException()
                }
            }
    }
}
