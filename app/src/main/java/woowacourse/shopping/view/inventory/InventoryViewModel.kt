package woowacourse.shopping.view.inventory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.inventory.InventoryRepository
import woowacourse.shopping.data.recent.RecentProductRepository
import woowacourse.shopping.data.shoppingcart.ShoppingCartRepository
import woowacourse.shopping.data.toCartItem
import woowacourse.shopping.data.toDomain
import woowacourse.shopping.domain.Page
import woowacourse.shopping.view.inventory.item.InventoryItem
import woowacourse.shopping.view.inventory.item.InventoryItem.ProductItem
import woowacourse.shopping.view.inventory.item.InventoryItem.RecentProductsItem
import woowacourse.shopping.view.inventory.item.InventoryItem.ShowMore

class InventoryViewModel(
    private val inventoryRepository: InventoryRepository,
    private val shoppingCartRepository: ShoppingCartRepository,
    private val recentProductRepository: RecentProductRepository,
) : ViewModel() {
    private val _items: MutableLiveData<List<InventoryItem>> = MutableLiveData(emptyList())
    val items: LiveData<List<InventoryItem>> get() = _items

    private val _inventoryUpdateEvent = MutableLiveData<ProductItem>()
    val inventoryUpdateEvent: LiveData<ProductItem> = _inventoryUpdateEvent

    private val _cartCount: MutableLiveData<Int> = MutableLiveData()
    val cartCount: LiveData<Int> get() = _cartCount

    fun loadCartCount() {
        shoppingCartRepository.getTotalCount { totalCount ->
            _cartCount.postValue(totalCount)
        }
    }

    fun requestPage() {
        shoppingCartRepository.getAll { allProducts ->
            allProducts.forEach { product -> inventoryRepository.insert(product.toDomain()) }
            val currentPageSize = _items.value?.filterIsInstance<ProductItem>()?.size ?: 0
            inventoryRepository.getPage(
                PAGE_SIZE,
                currentPageSize / PAGE_SIZE,
            ) { page -> updateInventoryProducts(page) }
        }
    }

    fun reloadPage() {
        _items.value = emptyList()
        requestPage()
    }

    fun increaseQuantity(product: ProductItem) {
        val updatedProduct = product.copy(quantity = product.quantity + 1)
        inventoryRepository.insert(updatedProduct)
        shoppingCartRepository.insert(updatedProduct.toCartItem())
        _inventoryUpdateEvent.value = updatedProduct
    }

    fun decreaseQuantity(product: ProductItem) {
        val updatedProduct = product.copy(quantity = product.quantity - 1)
        inventoryRepository.insert(updatedProduct)
        if (updatedProduct.quantity == 0) {
            shoppingCartRepository.delete(product.toCartItem())
        } else {
            shoppingCartRepository.insert(updatedProduct.toCartItem())
        }
        _inventoryUpdateEvent.value = updatedProduct
    }

    private fun updateInventoryProducts(newPage: Page<ProductItem>) {
        recentProductRepository.getMostRecent(RECENT_PRODUCTS_MAX_COUNT) { recentProducts ->
            val products = _items.value?.filterIsInstance<ProductItem>() ?: emptyList()
            val newList =
                buildList {
                    add(RecentProductsItem(recentProducts))
                    addAll(products)
                    addAll(newPage.items)
                    if (newPage.hasNext) add(ShowMore)
                }
            _items.postValue(newList)
        }
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
