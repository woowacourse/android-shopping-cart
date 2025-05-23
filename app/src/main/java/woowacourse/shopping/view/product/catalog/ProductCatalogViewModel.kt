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

    private var _count = MutableLiveData(0)
    val count: LiveData<Int> = _count

    private var currentPage = 0

    init {
        loadProducts()
    }

    fun loadProducts() {
        val result = productRepository.getPaged(PRODUCT_SIZE_LIMIT, currentPage * PRODUCT_SIZE_LIMIT)
        products.value = result
        currentPage++
    }

    fun addToShoppingCart(product: Product) {
        shoppingCartRepository.addProduct(product.id)
        _count.value = (_count.value?.plus(1)) ?: 0
    }

    fun removeToShoppingCart(productId: Long) {
        shoppingCartRepository.removeProduct(productId)
        _count.value = (_count.value?.minus(1)) ?: 0
    }

    private fun PagedResult<Product>.toProductItems(): List<ProductItem> {
        val items = this.items.map { ProductItem.CatalogProduct(it) }.toMutableList<ProductItem>()
        if (hasNext) {
            items.add(ProductItem.LoadMore)
        }
        return items
    }

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
