package woowacourse.shopping.view.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.ShoppingCartRepository
import woowacourse.shopping.view.inventory.item.InventoryItem
import woowacourse.shopping.view.inventory.item.toDomain

class ProductDetailViewModel(private val shoppingCartRepository: ShoppingCartRepository) : ViewModel() {
    fun addProduct(product: InventoryItem.ProductUiModel) {
        shoppingCartRepository.insert(product.toDomain())
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun createFactory(shoppingCartRepository: ShoppingCartRepository): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return (ProductDetailViewModel(shoppingCartRepository) as T)
                }
            }
        }
    }
}
