package woowacourse.shopping.view.inventory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.inventory.InventoryRepository
import woowacourse.shopping.data.recent.RecentProductRepository
import woowacourse.shopping.data.shoppingcart.ShoppingCartRepository
import woowacourse.shopping.data.toCartItem
import woowacourse.shopping.data.toUiModel
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.Page
import woowacourse.shopping.domain.Product
import woowacourse.shopping.view.inventory.adapter.InventoryItem
import woowacourse.shopping.view.inventory.adapter.InventoryItem.ProductUiModel
import woowacourse.shopping.view.inventory.adapter.InventoryItem.RecentProducts
import woowacourse.shopping.view.inventory.adapter.InventoryItem.ShowMore

class InventoryViewModel(
    private val inventoryRepository: InventoryRepository,
    private val shoppingCartRepository: ShoppingCartRepository,
    private val recentProductRepository: RecentProductRepository,
) : ViewModel() {
    private val _items: MutableLiveData<List<InventoryItem>> = MutableLiveData(emptyList())
    val items: LiveData<List<InventoryItem>> get() = _items

    private val _inventoryUpdateEvent = MutableLiveData<ProductUiModel>()
    val inventoryUpdateEvent: LiveData<ProductUiModel> = _inventoryUpdateEvent

    private val _cartCount: MutableLiveData<Int> = MutableLiveData()
    val cartCount: LiveData<Int> get() = _cartCount

    fun loadCartCount() {
        shoppingCartRepository.getTotalCount { totalCount ->
            _cartCount.postValue(totalCount)
        }
    }

    fun requestPage() {
        val currentPageSize = _items.value?.filterIsInstance<ProductUiModel>()?.size ?: 0
        inventoryRepository.getPage(PAGE_SIZE, currentPageSize / PAGE_SIZE) { page ->
            updateInventoryProducts(page)
        }
    }

    fun reloadPage() {
        _items.value = emptyList()
        requestPage()
    }

    fun increaseQuantity(product: ProductUiModel) {
        val updatedProduct = product.copy(quantity = product.quantity + 1)
        shoppingCartRepository.insert(updatedProduct.toCartItem())
        _inventoryUpdateEvent.value = updatedProduct
    }

    fun decreaseQuantity(product: ProductUiModel) {
        val updatedProduct = product.copy(quantity = product.quantity - 1)
        if (updatedProduct.quantity == 0) {
            shoppingCartRepository.delete(product.toCartItem())
        } else {
            shoppingCartRepository.insert(updatedProduct.toCartItem())
        }
        _inventoryUpdateEvent.value = updatedProduct
    }

    private fun updateInventoryProducts(newPage: Page<Product>) {
        shoppingCartRepository.getAll { cartProducts ->
            recentProductRepository.getMostRecent(RECENT_PRODUCTS_MAX_COUNT) { recentProducts ->
                val products = _items.value?.filterIsInstance<ProductUiModel>() ?: emptyList()
                val productUiModels = matchProductsToCartProducts(newPage.items, cartProducts)
                val newList =
                    buildList {
                        add(RecentProducts(recentProducts))
                        addAll(products)
                        addAll(productUiModels)
                        if (newPage.hasNext) add(ShowMore)
                    }
                _items.postValue(newList)
            }
        }
    }

    private fun matchProductsToCartProducts(
        products: List<Product>,
        cartProducts: List<CartProduct>,
    ): List<ProductUiModel> {
        val idToCartProduct =
            cartProducts.associateBy(
                { cartItem -> cartItem.id },
                { cartItem -> cartItem.toUiModel() },
            )

        val fetchedUiModels =
            products.map { product ->
                idToCartProduct[product.id] ?: product.toUiModel()
            }

        return fetchedUiModels
    }

    companion object {
        private const val PAGE_SIZE = 20
        private const val RECENT_PRODUCTS_MAX_COUNT = 10

        @Suppress("UNCHECKED_CAST")
        fun createFactory(
            inventoryRepository: InventoryRepository,
            shoppingCartRepository: ShoppingCartRepository,
            recentProductRepository: RecentProductRepository,
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return (
                        InventoryViewModel(
                            inventoryRepository,
                            shoppingCartRepository,
                            recentProductRepository,
                        ) as T
                    )
                }
            }
        }
    }
}
