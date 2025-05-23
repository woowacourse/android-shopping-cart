package woowacourse.shopping.view.inventory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.inventory.InventoryRepository
import woowacourse.shopping.data.shoppingcart.ShoppingCartRepository2
import woowacourse.shopping.domain.Page
import woowacourse.shopping.domain.Product
import woowacourse.shopping.view.inventory.item.InventoryItem
import woowacourse.shopping.view.inventory.item.InventoryItem.InventoryProduct
import woowacourse.shopping.view.inventory.item.InventoryItem.ShowMore
import woowacourse.shopping.view.inventory.item.toUiModel

class InventoryViewModel(
    private val inventoryRepository: InventoryRepository,
    private val shoppingCartRepository: ShoppingCartRepository2,
) : ViewModel() {
    private val _items: MutableLiveData<List<InventoryItem>> = MutableLiveData(emptyList())
    private val products: List<InventoryItem>
        get() = _items.value?.filterNot { item -> item == ShowMore } ?: emptyList()
    val items: LiveData<List<InventoryItem>> get() = _items

    fun requestPage() {
        inventoryRepository.getPage(
            PAGE_SIZE,
            products.size / PAGE_SIZE,
        ) { page -> updateItems(page) }
    }

    private fun updateItems(newPage: Page<Product>) {
        val newProducts: List<InventoryProduct> = newPage.items.map(Product::toUiModel)
        val newItems =
            products + newProducts + if (newPage.hasNext) listOf(ShowMore) else emptyList()
        _items.postValue(newItems)
    }

    companion object {
        private const val PAGE_SIZE = 20

        @Suppress("UNCHECKED_CAST")
        fun createFactory(
            inventoryRepository: InventoryRepository,
            shoppingCartRepository: ShoppingCartRepository2,
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return (
                        InventoryViewModel(
                            inventoryRepository,
                            shoppingCartRepository,
                        ) as T
                    )
                }
            }
        }
    }
}
