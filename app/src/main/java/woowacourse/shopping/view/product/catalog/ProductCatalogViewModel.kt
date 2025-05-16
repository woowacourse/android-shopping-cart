package woowacourse.shopping.view.product.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.domain.Product
import woowacourse.shopping.view.product.catalog.adapter.ProductCatalogEventHandler

class ProductCatalogViewModel(
    private val repository: ProductRepository,
) : ViewModel(),
    ProductCatalogEventHandler {
    private val items = mutableListOf<Product>()
    private val _products = MutableLiveData<List<Product>>(items)
    val products: LiveData<List<Product>> = _products

    private val _selectedProduct = MutableLiveData<Product>()
    val selectedProduct: LiveData<Product> = _selectedProduct

    private var offset = FIRST_OFFSET
    var hasNext = false
        private set

    init {
        loadProducts()
    }

    override fun onProductClick(item: Product) {
        _selectedProduct.value = item
    }

    override fun onMoreClick() {
        loadProducts()
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
    }
}
