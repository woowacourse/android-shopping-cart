package woowacourse.shopping.ui.productlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.domain.product.ProductRepository
import woowacourse.shopping.providers.RepositoryProvider

class ProductListViewModel(
    private val productRepository: ProductRepository,
) : ViewModel() {
    private val _products = MutableLiveData<List<ProductListViewType>>(emptyList())
    val products: LiveData<List<ProductListViewType>> get() = _products
    private var pageNumber = 0

    init {
        loadProducts()
    }

    fun loadProducts() {
        val originProducts =
            _products.value.orEmpty().filterIsInstance<ProductListViewType.ProductItemType>()

        productRepository.fetchInRange(
            limit = PAGE_FETCH_SIZE,
            offset = (pageNumber++) * PAGE_SIZE,
        ) { loadProducts ->
            val newProducts =
                loadProducts.take(PAGE_SIZE)
                    .map { product -> ProductListViewType.ProductItemType(product) }

            if (hasNextPage(loadProducts)) {
                _products.postValue(originProducts + newProducts + ProductListViewType.LoadMoreType)
            } else {
                _products.postValue(originProducts + newProducts)
            }
        }
    }

    private fun hasNextPage(loadProducts: List<Product>) = loadProducts.size > PAGE_SIZE

    companion object {
        private const val PAGE_FETCH_SIZE = 21
        private const val PAGE_SIZE = 20

        val Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ProductListViewModel(RepositoryProvider.provideProductRepository()) as T
                }
            }
    }
}
