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
import woowacourse.shopping.view.util.MutableSingleLiveData
import woowacourse.shopping.view.util.SingleLiveData

class ProductCatalogViewModel(
    private val productRepository: ProductRepository,
    private val cartProductRepository: CartProductRepository,
    private val recentProductRepository: RecentProductRepository,
) : ViewModel(),
    ProductCatalogEventHandler {
    private val productItems = mutableListOf<ProductCatalogItem.ProductItem>()
    private var recentProducts = emptyList<RecentProduct>()
    private var offset = FIRST_OFFSET
    private var hasNext = false

    private val _productCatalogItems = MutableLiveData<List<ProductCatalogItem>>()
    val productCatalogItems: LiveData<List<ProductCatalogItem>> get() = _productCatalogItems

    private val _selectedProduct = MutableSingleLiveData<Product>()
    val selectedProduct: SingleLiveData<Product> get() = _selectedProduct

    private val _totalQuantity = MutableLiveData(0)
    val totalQuantity: LiveData<Int> get() = _totalQuantity

    override fun onRecentProductClick(item: RecentProduct) {
        _selectedProduct.setValue(item.product)
    }

    override fun onProductClick(item: ProductCatalogItem.ProductItem) {
        _selectedProduct.setValue(item.product)
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

    fun loadCatalog() {
        loadRecentProducts()
        if (productItems.isEmpty()) {
            loadProducts()
        } else {
            syncProductQuantities()
        }
        cartProductRepository.getTotalQuantity {
            _totalQuantity.postValue(it)
        }
    }

    private fun loadRecentProducts() {
        recentProductRepository.getPagedProducts(RECENT_PRODUCT_SIZE_LIMIT) {
            recentProducts = it
            _productCatalogItems.postValue(buildCatalogItems())
        }
    }

    private fun loadProducts() {
        productRepository.getPagedProducts(PRODUCT_SIZE_LIMIT, offset) { result ->
            offset += result.items.size

            val tempItems = mutableListOf<ProductCatalogItem.ProductItem>()
            var completedCount = 0
            result.items.forEach { product ->
                cartProductRepository.getQuantityByProductId(product.id) { quantity ->
                    tempItems.add(ProductCatalogItem.ProductItem(product, quantity ?: 0))
                    completedCount++

                    if (completedCount == result.items.size) {
                        productItems.addAll(tempItems)
                        hasNext = result.hasNext
                        _productCatalogItems.postValue(buildCatalogItems())
                    }
                }
            }
        }
    }

    private fun syncProductQuantities() {
        val updatedItems = mutableListOf<ProductCatalogItem.ProductItem>()
        var completedCount = 0

        productItems.forEach { item ->
            cartProductRepository.getQuantityByProductId(item.product.id) { quantity ->
                updatedItems.add(item.copy(quantity = quantity ?: 0))
                completedCount++

                if (completedCount == productItems.size) {
                    productItems.clear()
                    productItems.addAll(updatedItems)
                    _productCatalogItems.postValue(buildCatalogItems())
                }
            }
        }
    }

    private fun updateQuantity(
        item: ProductCatalogItem.ProductItem,
        newQuantity: Int,
    ) {
        cartProductRepository.updateQuantity(item.product.id, item.quantity, newQuantity) {
            val index = productItems.indexOfFirst { it.product.id == item.product.id }
            if (index != -1) {
                productItems[index] = productItems[index].copy(quantity = newQuantity)
            }
            _totalQuantity.postValue((totalQuantity.value ?: 0) + (newQuantity - item.quantity))
            _productCatalogItems.postValue(buildCatalogItems())
        }
    }

    private fun buildCatalogItems(): List<ProductCatalogItem> =
        buildList {
            if (recentProducts.isNotEmpty()) {
                add(ProductCatalogItem.RecentProductsItem(recentProducts))
            }
            addAll(productItems.sortedBy { it.product.id })
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
