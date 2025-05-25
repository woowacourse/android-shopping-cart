package woowacourse.shopping.view.inventory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.inventory.InventoryRepository
import woowacourse.shopping.data.recent.RecentProductRepository
import woowacourse.shopping.data.shoppingcart.ShoppingCartRepository
import woowacourse.shopping.data.toCartItem
import woowacourse.shopping.data.toInventoryProduct
import woowacourse.shopping.domain.Page
import woowacourse.shopping.domain.RecentProduct
import woowacourse.shopping.view.inventory.item.InventoryItem
import woowacourse.shopping.view.inventory.item.InventoryItem.InventoryProduct
import woowacourse.shopping.view.inventory.item.InventoryItem.RecentProducts
import woowacourse.shopping.view.inventory.item.InventoryItem.ShowMore
import java.time.LocalDateTime
import java.time.ZoneId

class InventoryViewModel(
    private val inventoryRepository: InventoryRepository,
    private val shoppingCartRepository: ShoppingCartRepository,
    private val recentProductRepository: RecentProductRepository,
) : ViewModel() {
    private val _items: MutableLiveData<List<InventoryItem>> = MutableLiveData(emptyList())
    private val products: List<InventoryItem>
        get() = _items.value?.filterIsInstance<InventoryProduct>() ?: emptyList()
    val items: LiveData<List<InventoryItem>> get() = _items

    private val _cartCount: MutableLiveData<Int> = MutableLiveData()
    val cartCount: LiveData<Int> get() = _cartCount

    fun loadCartCount() {
        shoppingCartRepository.getTotalCount { totalCount ->
            _cartCount.postValue(totalCount)
        }
    }

    fun requestPage() {
        shoppingCartRepository.getAll { allItems ->
            allItems.forEach { item ->
                inventoryRepository.insert(item.toInventoryProduct())
            }
            inventoryRepository.getPage(
                PAGE_SIZE,
                products.size / PAGE_SIZE,
            ) { page -> updateItems(page) }
        }
    }

    fun reloadPage() {
        _items.value = emptyList()
        requestPage()
    }

    fun increaseQuantity(
        position: Int,
        product: InventoryProduct,
    ) {
        val updatedProduct = product.copy(quantity = product.quantity + 1)
        (_items.value ?: emptyList()).toMutableList().let { newList ->
            newList[position] = updatedProduct
            _items.postValue(newList)
        }
        inventoryRepository.insert(updatedProduct)
        shoppingCartRepository.insert(updatedProduct.toCartItem())
    }

    fun decreaseQuantity(
        position: Int,
        product: InventoryProduct,
    ) {
        val updatedProduct = product.copy(quantity = product.quantity - 1)
        (_items.value ?: emptyList()).toMutableList().let { newList ->
            newList[position] = updatedProduct
            _items.postValue(newList)
        }
        inventoryRepository.insert(updatedProduct)
        if (updatedProduct.quantity == 0) {
            shoppingCartRepository.delete(product.toCartItem())
            return
        }
        shoppingCartRepository.insert(updatedProduct.toCartItem())
    }

    private fun updateItems(newPage: Page<InventoryProduct>) {
        recentProductRepository.getMostRecent(RECENT_PRODUCTS_MAX_COUNT) { recentProducts ->
            val newList =
                buildList {
                    add(RecentProducts(recentProducts))
                    addAll(products)
                    addAll(newPage.items)
                    if (newPage.hasNext) add(ShowMore)
                }
            _items.postValue(newList)
        }
    }

    fun updateRecentProducts(product: InventoryProduct) {
        val time = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        val recentProduct = RecentProduct(product.id, product.name, product.imageUrl, time)
        recentProductRepository.insert(recentProduct)
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
