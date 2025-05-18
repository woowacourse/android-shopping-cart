package woowacourse.shopping.view.inventory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.InventoryRepository
import woowacourse.shopping.domain.Product
import woowacourse.shopping.view.inventory.item.InventoryItem
import woowacourse.shopping.view.inventory.item.InventoryItem.ProductUiModel
import woowacourse.shopping.view.inventory.item.InventoryItem.ShowMore
import woowacourse.shopping.view.inventory.item.toUiModel

class InventoryViewModel(private val repository: InventoryRepository) : ViewModel() {
    private val products: List<ProductUiModel> = repository.getAll().map(Product::toUiModel)
    private val _items: MutableLiveData<List<InventoryItem>> = MutableLiveData(emptyList())
    val items: LiveData<List<InventoryItem>> get() = _items
    val totalSize: Int get() = products.size

    fun requestPage() {
        _items.value = _items.value?.minus(ShowMore)
        val page = repository.getPage(PAGE_SIZE, (_items.value?.size ?: 0) / PAGE_SIZE)
        _items.value = _items.value?.plus(page.items.map(Product::toUiModel))
        if (page.hasNext) _items.value = _items.value?.plus(ShowMore)
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
