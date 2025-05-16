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
    private val items = mutableListOf<Product>()
    private val _products = MutableLiveData<List<Product>>(items)
    val products: LiveData<List<Product>> = _products

    private var offset = FIRST_OFFSET
    var hasNext = false
        private set

    init {
        loadProducts()
    }

    fun loadMoreProducts() {
        val result =
            repository.getPagedProducts(
                PRODUCT_SIZE_LIMIT,
                offset,
            )
        hasNext = result.hasNext
        _products.value = products.value.orEmpty() + result.items
        offset += result.items.size
    }

    private fun loadProducts() {
        val result = repository.getPagedProducts(PRODUCT_SIZE_LIMIT, offset)
        hasNext = result.hasNext
        _products.value = products.value.orEmpty() + result.items
        offset += result.items.size
    }

    companion object {
        private const val FIRST_OFFSET = 0
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
