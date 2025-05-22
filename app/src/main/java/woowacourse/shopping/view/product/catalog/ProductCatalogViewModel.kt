package woowacourse.shopping.view.product.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.cart.CartProductRepository
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.data.recent.RecentProductRepository
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.RecentProduct
import woowacourse.shopping.view.product.catalog.adapter.ProductCatalogItem

class ProductCatalogViewModel(
    private val productRepository: ProductRepository,
    private val cartProductRepository: CartProductRepository,
    private val recentProductRepository: RecentProductRepository,
) : ViewModel(),
    ProductCatalogEventHandler {
    private val products = mutableMapOf<Product, Int>()
    private var offset = FIRST_OFFSET

    private val _productItems = MutableLiveData<List<ProductCatalogItem>>()
    val productItems: LiveData<List<ProductCatalogItem>> = _productItems

    private val _selectedProduct = MutableLiveData<Product>()
    val selectedProduct: LiveData<Product> = _selectedProduct

    private val _totalQuantity = MutableLiveData(0)
    val totalQuantity: LiveData<Int> = _totalQuantity

    init {
        loadMoreProducts()
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
        _totalQuantity.value = totalQuantity.value?.plus(1)
    }

    override fun onQuantityIncreaseClick(item: Product) {
        val currentQuantity = products[item] ?: 0
        val newQuantity = currentQuantity + 1
        if (currentQuantity == 0) {
            cartProductRepository.insert(item.id)
        } else {
            cartProductRepository.updateQuantity(item.id, newQuantity)
        }
        updateProductQuantity(item, newQuantity)
        _totalQuantity.value = totalQuantity.value?.plus(1)
    }

    override fun onQuantityDecreaseClick(item: Product) {
        val currentQuantity = products[item] ?: 0
        val newQuantity = currentQuantity - 1
        if (currentQuantity == 1) {
            cartProductRepository.deleteByProductId(item.id)
        } else {
            cartProductRepository.updateQuantity(item.id, newQuantity)
        }
        updateProductQuantity(item, newQuantity)
        _totalQuantity.value = totalQuantity.value?.minus(1)
    }

    override fun onMoreClick() {
        loadMoreProducts()
    }

    private fun loadMoreProducts() {
        val recentProducts = recentProductRepository.getPagedProducts(RECENT_PRODUCT_SIZE_LIMIT)

        val result = productRepository.getPagedProducts(PRODUCT_SIZE_LIMIT, offset)
        result.items.forEach { product ->
            val quantity = cartProductRepository.getQuantityByProductId(product.id)
            products[product] = quantity ?: 0
        }
        offset += result.items.size
        _productItems.value = createProductItems(recentProducts, result.hasNext)
    }

    private fun updateProductQuantity(
        product: Product,
        quantity: Int,
    ) {
        products[product] = quantity
        _productItems.value =
            productItems.value?.map {
                when (it) {
                    is ProductCatalogItem.RecentProductsItem -> it
                    is ProductCatalogItem.ProductItem ->
                        if (it.product.id == product.id) it.copy(quantity = quantity) else it

                    ProductCatalogItem.LoadMoreItem -> it
                }
            }
    }

    private fun createProductItems(
        recentProducts: List<RecentProduct>,
        hasNext: Boolean,
    ): List<ProductCatalogItem> {
        val items =
            products.map { (product, quantity) ->
                ProductCatalogItem.ProductItem(product, quantity)
            }
        return buildList {
            if (recentProducts.isNotEmpty()) add(ProductCatalogItem.RecentProductsItem(recentProducts))
            addAll(items)
            if (hasNext) add(ProductCatalogItem.LoadMoreItem)
        }
    }

    companion object {
        private const val FIRST_OFFSET = 0
        private const val PRODUCT_SIZE_LIMIT = 20
        private const val RECENT_PRODUCT_SIZE_LIMIT = 10
    }
}
