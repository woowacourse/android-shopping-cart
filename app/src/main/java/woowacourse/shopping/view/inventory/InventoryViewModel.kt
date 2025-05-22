package woowacourse.shopping.view.inventory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.InventoryRepository
import woowacourse.shopping.domain.Product
import woowacourse.shopping.view.inventory.item.InventoryItem
import woowacourse.shopping.view.inventory.item.InventoryItem.ShowMore
import woowacourse.shopping.view.inventory.item.toUiModel

class InventoryViewModel(private val repository: InventoryRepository) : ViewModel() {
    private val _items: MutableLiveData<List<InventoryItem>> = MutableLiveData(emptyList())
    val items: LiveData<List<InventoryItem>> get() = _items

    fun requestPage() {
        val currentProducts = _items.value ?: emptyList()
        val newPage = repository.getPage(PAGE_SIZE, currentProducts.size / PAGE_SIZE)
        val newProducts: List<InventoryItem> = newPage.items.map(Product::toUiModel)
        val newItems = newProducts + if (newPage.hasNext) listOf(ShowMore) else emptyList()
        _items.value = newItems
    }

    companion object {
        private const val PAGE_SIZE = 20

        @Suppress("UNCHECKED_CAST")
        fun createFactory(inventoryRepository: InventoryRepository): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return (InventoryViewModel(inventoryRepository) as T)
                }
            }
        }
    }
}
