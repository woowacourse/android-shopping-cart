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
    private val products = mutableListOf<ProductCatalogItem.ProductItem>()
    private var recentProducts = emptyList<RecentProduct>()
    private var offset = FIRST_OFFSET

    private val _productCatalogItems = MutableLiveData<List<ProductCatalogItem>>()
    val productCatalogItems: LiveData<List<ProductCatalogItem>> = _productCatalogItems

    private val _selectedProduct = MutableLiveData<Product>()
    val selectedProduct: LiveData<Product> = _selectedProduct

    private val _totalQuantity = MutableLiveData(0)
    val totalQuantity: LiveData<Int> = _totalQuantity

    init {
        loadRecentProducts()
        loadProducts()
        _totalQuantity.value = cartProductRepository.getTotalQuantity()
    }

    override fun onRecentProductClick(item: RecentProduct) {
        _selectedProduct.value = item.product
    }

    override fun onProductClick(item: Product) {
        _selectedProduct.value = item
    }

    override fun onAddClick(item: Product) {
        cartProductRepository.insert(item.id)
        updateProductQuantity(item, 1)
        increaseTotalQuantity(1)
    }

    override fun onQuantityIncreaseClick(item: ProductCatalogItem.ProductItem) {
        val newQuantity = item.quantity + 1
        if (item.quantity == 0) {
            cartProductRepository.insert(item.product.id)
        } else {
            cartProductRepository.updateQuantity(item.product.id, newQuantity)
        }
        updateProductQuantity(item.product, newQuantity)
        increaseTotalQuantity(1)
    }

    override fun onQuantityDecreaseClick(item: ProductCatalogItem.ProductItem) {
        val newQuantity = item.quantity - 1
        if (item.quantity == 1) {
            cartProductRepository.deleteByProductId(item.product.id)
        } else {
            cartProductRepository.updateQuantity(item.product.id, newQuantity)
        }
        updateProductQuantity(item.product, newQuantity)
        increaseTotalQuantity(-1)
    }

    override fun onMoreClick() {
        loadProducts()
    }

    private fun loadRecentProducts() {
        recentProducts = recentProductRepository.getPagedProducts(RECENT_PRODUCT_SIZE_LIMIT)
    }

    private fun loadProducts() {
        val result = productRepository.getPagedProducts(PRODUCT_SIZE_LIMIT, offset)
        _productCatalogItems.value = buildProductCatalogItems(result.items, result.hasNext)
        offset += result.items.size
    }

    private fun buildProductCatalogItems(
        newProducts: List<Product>,
        hasNext: Boolean,
    ): List<ProductCatalogItem> =
        buildList {
            if (recentProducts.isNotEmpty()) {
                add(ProductCatalogItem.RecentProductsItem(recentProducts))
            }
            addAll(products)
            addAll(
                newProducts.map { product ->
                    val quantity = cartProductRepository.getQuantityByProductId(product.id) ?: 0
                    val productItem = ProductCatalogItem.ProductItem(product, quantity)
                    products.add(productItem)
                    productItem
                },
            )
            if (hasNext) add(ProductCatalogItem.LoadMoreItem)
        }

    private fun increaseTotalQuantity(delta: Int) {
        _totalQuantity.value = (_totalQuantity.value ?: 0) + delta
    }

    private fun updateProductQuantity(
        product: Product,
        quantity: Int,
    ) {
        _productCatalogItems.value =
            productCatalogItems.value?.map {
                when (it) {
                    is ProductCatalogItem.ProductItem ->
                        if (it.product.id == product.id) it.copy(quantity = quantity) else it

                    else -> it
                }
            }
    }

    companion object {
        private const val FIRST_OFFSET = 0
        private const val PRODUCT_SIZE_LIMIT = 20
        private const val RECENT_PRODUCT_SIZE_LIMIT = 10
    }
}
