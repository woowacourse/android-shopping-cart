package woowacourse.shopping.view.product.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.data.product.ProductRepositoryImpl
import woowacourse.shopping.domain.Product

class ProductCatalogViewModel(
    private val repository: ProductRepository,
) : ViewModel() {
    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    private val _currentPage = MutableLiveData(0)
    val currentPage: LiveData<Int> = _currentPage

    init {
        loadProducts()
    }

    fun loadMoreProducts() {
        val newProducts =
            repository.getPaged(
                PRODUCT_SIZE_LIMIT,
                currentPage.value?.times(PRODUCT_SIZE_LIMIT) ?: 0,
            )
        _currentPage.value = _currentPage.value?.plus(1)
        _products.value = _products.value?.plus(newProducts)
    }

    private fun loadProducts() {
        _products.value = repository.getPaged(PRODUCT_SIZE_LIMIT, 0)
        _currentPage.value = currentPage.value?.plus(1)
    }

    companion object {
        private const val PRODUCT_SIZE_LIMIT = 20

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
