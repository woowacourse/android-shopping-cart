package woowacourse.shopping.view.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.inventory.InventoryRepository
import woowacourse.shopping.data.shoppingcart.ShoppingCartRepository2
import woowacourse.shopping.data.toCartItem
import woowacourse.shopping.view.inventory.item.InventoryItem.InventoryProduct

class ProductDetailViewModel(
    private val inventoryRepository: InventoryRepository,
    private val shoppingCartRepository: ShoppingCartRepository2,
) : ViewModel() {
    fun addProduct(product: InventoryProduct) {
        shoppingCartRepository.getOrNull(product.id) { cartItem ->
            val item =
                if (cartItem == null) {
                    product.copy(quantity = 1)
                } else {
                    product.copy(quantity = cartItem.quantity + 1)
                }
            inventoryRepository.insert(item)
            shoppingCartRepository.insert(item.toCartItem())
        }
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun createFactory(
            inventoryRepository: InventoryRepository,
            shoppingCartRepository: ShoppingCartRepository2,
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return (
                        ProductDetailViewModel(
                            inventoryRepository,
                            shoppingCartRepository,
                        ) as T
                    )
                }
            }
        }
    }
}
