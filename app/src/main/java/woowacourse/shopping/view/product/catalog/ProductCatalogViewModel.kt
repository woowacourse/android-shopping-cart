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
import woowacourse.shopping.domain.ShoppingProduct
import woowacourse.shopping.view.PagedResult

class ProductCatalogViewModel(
    private val productRepository: ProductRepository,
    private val shoppingCartRepository: ShoppingCartRepository,
) : ViewModel() {
    private val products = MutableLiveData<PagedResult<ShoppingProduct>>()
    val productItems: LiveData<List<ProductItem>> = products.map { it.toProductItems() }

    private var currentPage = 0

    init {
        loadProducts()
    }

    fun loadProducts() {
        val result = productRepository.getPaged(PRODUCT_SIZE_LIMIT, currentPage * PRODUCT_SIZE_LIMIT)
        products.value = result.toShoppingProduct()
        currentPage++
    }

    private fun PagedResult<Product>.toShoppingProduct(): PagedResult<ShoppingProduct> {
        val shoppingProduct = this.items.map { shoppingCartRepository.get(it.id) ?: ShoppingProduct(it.id, 0) }
        return PagedResult(shoppingProduct, this.hasNext)
    }

    fun addToShoppingCart(product: Product) {
        val old = products.value ?: return

        shoppingCartRepository.addProduct(product.id)

        val updatedItems =
            old.items.map {
                if (it.productId == product.id) {
                    it.copy(quantity = it.quantity?.plus(1))
                } else {
                    it
                }
            }

        products.value = PagedResult(updatedItems, old.hasNext)
    }

    fun removeToShoppingCart(productId: Long) {
        val old = products.value ?: return

        shoppingCartRepository.removeProduct(productId)

        val updatedItems =
            old.items.map {
                if (it.productId == productId) {
                    it.copy(quantity = it.quantity?.minus(1))
                } else {
                    it
                }
            }

        products.value = PagedResult(updatedItems, old.hasNext)
    }

    private fun PagedResult<ShoppingProduct>.toProductItems(): List<ProductItem> {
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
