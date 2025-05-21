package woowacourse.shopping.view.product.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.cart.CartProductRepository
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.domain.Product
import woowacourse.shopping.view.product.catalog.adapter.ProductCatalogItem

class ProductCatalogViewModel(
    private val productRepository: ProductRepository,
    private val cartProductRepository: CartProductRepository,
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

    override fun onQuantityIncreaseClick(item: Product) {
    }

    override fun onQuantityDecreaseClick(item: Product) {
    }

    override fun onMoreClick() {
        loadMoreProducts()
    }

    private fun loadMoreProducts() {
        val result = productRepository.getPagedProducts(PRODUCT_SIZE_LIMIT, offset)
        products.addAll(result.items)
        offset += result.items.size
        val items =
            products.map {
                val quantity = cartProductRepository.getQuantityByProductId(it.id)
                ProductCatalogItem.ProductItem(it, quantity ?: 0)
            }
        _productItems.value = if (result.hasNext) items + ProductCatalogItem.LoadMoreItem else items
    }

    companion object {
        private const val FIRST_OFFSET = 0
        private const val PRODUCT_SIZE_LIMIT = 20
    }
}
