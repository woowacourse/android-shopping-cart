package woowacourse.shopping.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    private val _quantity = MutableLiveData(PRODUCT_MINIMUM_QUANTITY)
    val quantity: LiveData<Int> get() = _quantity

    fun addToCart(product: InventoryProduct) {
        shoppingCartRepository.getOrNull(product.id) { cartItem ->
            val currentQuantity = cartItem?.quantity ?: 0
            val item = product.copy(quantity = currentQuantity + (quantity.value ?: 0))
            inventoryRepository.insert(item)
            shoppingCartRepository.insert(item.toCartItem())
        }
    }

    fun decreaseQuantity() {
        if (_quantity.value == PRODUCT_MINIMUM_QUANTITY) return
        _quantity.value = _quantity.value?.minus(1)
    }

    fun increaseQuantity() {
        _quantity.value = _quantity.value?.plus(1)
    }

    companion object {
        private const val PRODUCT_MINIMUM_QUANTITY = 1

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
