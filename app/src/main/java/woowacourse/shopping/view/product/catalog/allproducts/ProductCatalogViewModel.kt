package woowacourse.shopping.view.product.catalog.allproducts

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import woowacourse.shopping.ShoppingProvider
import woowacourse.shopping.data.mapper.toProductDomain
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.data.recentlyproducts.RecentlyProductsRepository
import woowacourse.shopping.data.shoppingcart.ShoppingCartRepository
import woowacourse.shopping.domain.Product
import woowacourse.shopping.view.PagedResult

class ProductCatalogViewModel(
    private val productRepository: ProductRepository,
    private val shoppingCartRepository: ShoppingCartRepository,
    private val recentlyProductsRepository: RecentlyProductsRepository,
) : ViewModel() {
    private val pageResultProducts = MutableLiveData<PagedResult<Product>>()
    private val _products = MutableLiveData<List<Product>>(emptyList())
    val products: LiveData<List<Product>> = _products

    val productItems: LiveData<List<ProductItem>> = pageResultProducts.map { it.toProductItems() }

    private var currentPage = 0

    init {
        initLoadProducts()
    }

    private fun initLoadProducts() {
        val result = productRepository.getPaged(PRODUCT_SIZE_LIMIT, currentPage * PRODUCT_SIZE_LIMIT)
        pageResultProducts.value = result
        currentPage++
        _products.value = recentlyProductsRepository.getAll()?.map { it.toProductDomain() } ?: emptyList()
        Log.d("asdf", "products : ${_products.value}")
    }

    fun loadProducts() {
        val result = productRepository.getPaged(PRODUCT_SIZE_LIMIT, currentPage * PRODUCT_SIZE_LIMIT)
        pageResultProducts.value = pageResultProducts.value?.plus(result)
        currentPage++
    }

    fun addToRecentlyProduct(product: Product) {
        Log.d("asdf", "product : $product")
        recentlyProductsRepository.insert(product)
        Log.d("asdf", "적재 : ${recentlyProductsRepository.getAll()}")
    }

    fun addToShoppingCart(productId: Long) {
        shoppingCartRepository.addProduct(productId)
        pageResultProducts.value = pageResultProducts.value
    }

    fun removeToShoppingCart(productId: Long) {
        shoppingCartRepository.removeProduct(productId)
        pageResultProducts.value = pageResultProducts.value
    }

    private fun PagedResult<Product>.toProductItems(): List<ProductItem> {
        val items =
            this.items
                .map {
                    ProductItem.CatalogProduct(
                        it,
                        findQuantity(it.id),
                    )
                }.toMutableList<ProductItem>()
        if (hasNext) {
            items.add(ProductItem.LoadMore)
        }
        return items
    }

    private fun findQuantity(productId: Long): Int = shoppingCartRepository.getQuantity(productId)

    companion object {
        private const val PRODUCT_SIZE_LIMIT = 20

        fun provideFactory(
            productRepository: ProductRepository = ShoppingProvider.productRepository,
            shoppingCartRepository: ShoppingCartRepository = ShoppingProvider.shoppingCartRepository,
            recentlyProductsRepository: RecentlyProductsRepository = ShoppingProvider.recentlyProductsRepository,
        ): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(ProductCatalogViewModel::class.java)) {
                        return ProductCatalogViewModel(
                            productRepository,
                            shoppingCartRepository,
                            recentlyProductsRepository,
                        ) as T
                    }
                    throw IllegalArgumentException()
                }
            }
    }
}
