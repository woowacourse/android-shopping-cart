package woowacourse.shopping.view.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.inventory.InventoryRepository
import woowacourse.shopping.view.inventory.item.InventoryItem
import woowacourse.shopping.view.inventory.item.toDomain

class ProductDetailViewModel(
    private val repository: InventoryRepository,
) : ViewModel() {
    fun addProduct(product: InventoryItem.ProductUiModel) {
        repository.insert(product.toDomain())
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun createFactory(repository: InventoryRepository): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return (ProductDetailViewModel(repository) as T)
                }
            }
        }
    }
}
