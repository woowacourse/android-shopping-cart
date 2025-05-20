package woowacourse.shopping.view.product.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.domain.Product
import woowacourse.shopping.view.product.catalog.adapter.ProductCatalogItem

class ProductCatalogViewModel(
    private val repository: ProductRepository,
) : ViewModel(),
    ProductCatalogEventHandler {
    private val products = mutableListOf<Product>()

    private val _productItems = MutableLiveData<List<ProductCatalogItem>>()
    val productItems: LiveData<List<ProductCatalogItem>> = _productItems

    private val _selectedProduct = MutableLiveData<Product>()
    val selectedProduct: LiveData<Product> = _selectedProduct

    private var offset = FIRST_OFFSET

    init {
        loadMoreProducts()
    }

    override fun onProductClick(item: Product) {
        _selectedProduct.value = item
    }

    override fun onMoreClick() {
        loadMoreProducts()
    }

    private fun loadMoreProducts() {
        val result = repository.getPagedProducts(PRODUCT_SIZE_LIMIT, offset)
        products.addAll(result.items)
        offset += result.items.size
        val items = products.map { ProductCatalogItem.ProductItem(it) }
        _productItems.value = if (result.hasNext) items + ProductCatalogItem.LoadMoreItem else items
    }

    companion object {
        private const val FIRST_OFFSET = 0
        private const val PRODUCT_SIZE_LIMIT = 20
    }
}
