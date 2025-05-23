package woowacourse.shopping.view.product.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import woowacourse.shopping.ShoppingProvider
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.data.shoppingcart.ShoppingCartRepository
import woowacourse.shopping.domain.Product
import woowacourse.shopping.view.PagedResult

class ProductCatalogViewModel(
    private val productRepository: ProductRepository,
    private val shoppingCartRepository: ShoppingCartRepository,
) : ViewModel() {
    private val products = MutableLiveData<PagedResult<Product>>()

    val productItems: LiveData<List<ProductItem>> = products.map { it.toProductItems() }

    private var currentPage = 0

    init {
        initLoadProducts()
    }

    private fun initLoadProducts() {
        val result = productRepository.getPaged(PRODUCT_SIZE_LIMIT, currentPage * PRODUCT_SIZE_LIMIT)
        products.value = result
        currentPage++
    }

    fun loadProducts() {
        val result = productRepository.getPaged(PRODUCT_SIZE_LIMIT, currentPage * PRODUCT_SIZE_LIMIT)
        products.value = products.value?.plus(result)
        currentPage++
    }

    fun addToShoppingCart(productId: Long) {
        shoppingCartRepository.addProduct(productId)
        products.value = products.value
    }

    fun removeToShoppingCart(productId: Long) {
        shoppingCartRepository.removeProduct(productId)
        products.value = products.value
    }

    private fun PagedResult<Product>.toProductItems(): List<ProductItem> {
        val items = this.items.map { ProductItem.CatalogProduct(it, findQuantity(it.id)) }.toMutableList<ProductItem>()
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
        ): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(ProductCatalogViewModel::class.java)) {
                        return ProductCatalogViewModel(
                            productRepository,
                            shoppingCartRepository,
                        ) as T
                    }
                    throw IllegalArgumentException()
                }
            }
    }
}
