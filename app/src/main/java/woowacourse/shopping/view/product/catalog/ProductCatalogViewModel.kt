package woowacourse.shopping.view.product.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.RecentProduct
import woowacourse.shopping.domain.repository.CartProductRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.RecentProductRepository
import woowacourse.shopping.view.product.catalog.adapter.ProductCatalogItem

class ProductCatalogViewModel(
    private val productRepository: ProductRepository,
    private val cartProductRepository: CartProductRepository,
    private val recentProductRepository: RecentProductRepository,
) : ViewModel(),
    ProductCatalogEventHandler {
    private val productItems = mutableListOf<ProductCatalogItem.ProductItem>()
    private var recentProducts = emptyList<RecentProduct>()
    private var offset = FIRST_OFFSET

    private val _productCatalogItems = MutableLiveData<List<ProductCatalogItem>>()
    val productCatalogItems: LiveData<List<ProductCatalogItem>> get() = _productCatalogItems

    private val _selectedProduct = MutableLiveData<Product>()
    val selectedProduct: LiveData<Product> get() = _selectedProduct

    private val _totalQuantity = MutableLiveData(0)
    val totalQuantity: LiveData<Int> get() = _totalQuantity

    init {
        loadRecentProducts()
        loadProducts()
        _totalQuantity.value = cartProductRepository.getTotalQuantity()
    }

    override fun onRecentProductClick(item: RecentProduct) {
        _selectedProduct.value = item.product
    }

    override fun onProductClick(item: ProductCatalogItem.ProductItem) {
        _selectedProduct.value = item.product
    }

    override fun onAddClick(item: ProductCatalogItem.ProductItem) {
        updateQuantity(item, 1)
    }

    override fun onQuantityIncreaseClick(item: ProductCatalogItem.ProductItem) {
        updateQuantity(item, item.quantity + 1)
    }

    override fun onQuantityDecreaseClick(item: ProductCatalogItem.ProductItem) {
        updateQuantity(item, item.quantity - 1)
    }

    override fun onMoreClick() {
        loadProducts()
    }

    private fun loadRecentProducts() {
        recentProducts = recentProductRepository.getPagedProducts(RECENT_PRODUCT_SIZE_LIMIT)
    }

    private fun loadProducts() {
        val result = productRepository.getPagedProducts(PRODUCT_SIZE_LIMIT, offset)
        offset += result.items.size

        val newItems =
            result.items.map {
                val quantity = cartProductRepository.getQuantityByProductId(it.id) ?: 0
                ProductCatalogItem.ProductItem(it, quantity)
            }
        productItems.addAll(newItems)
        _productCatalogItems.value = buildCatalogItems(result.hasNext)
    }

    private fun updateQuantity(
        item: ProductCatalogItem.ProductItem,
        newQuantity: Int,
    ) {
        cartProductRepository.updateQuantity(item.product.id, item.quantity, newQuantity)
        val index = productItems.indexOf(item)
        if (index != -1) {
            productItems[index] = productItems[index].copy(quantity = newQuantity)
        }

        _totalQuantity.value = (totalQuantity.value ?: 0) + (newQuantity - item.quantity)
        _productCatalogItems.value = buildCatalogItems()
    }

    private fun buildCatalogItems(hasNext: Boolean = false): List<ProductCatalogItem> =
        buildList {
            if (recentProducts.isNotEmpty()) {
                add(ProductCatalogItem.RecentProductsItem(recentProducts))
            }
            addAll(productItems)
            if (hasNext) {
                add(ProductCatalogItem.LoadMoreItem)
            }
        }

    companion object {
        private const val FIRST_OFFSET = 0
        private const val PRODUCT_SIZE_LIMIT = 20
        private const val RECENT_PRODUCT_SIZE_LIMIT = 10
    }
}
