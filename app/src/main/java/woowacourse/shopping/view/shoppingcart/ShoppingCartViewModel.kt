package woowacourse.shopping.view.shoppingcart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.inventory.InventoryRepository
import woowacourse.shopping.data.shoppingcart.ShoppingCartRepository2
import woowacourse.shopping.data.toInventoryProduct
import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.domain.Page

class ShoppingCartViewModel(
    private val inventoryRepository: InventoryRepository,
    private val shoppingCartRepository: ShoppingCartRepository2,
) : ViewModel() {
    private val _cartItems = MutableLiveData<Page<CartItem>>()
    val cartItems: LiveData<Page<CartItem>> get() = _cartItems

    fun requestPage(pageIndex: Int) {
        shoppingCartRepository.getPage(PAGE_SIZE, pageIndex) { page ->
            if (page.items.isEmpty()) {
                requestPreviousPage()
            } else {
                _cartItems.postValue(page)
            }
        }
    }

    fun requestPreviousPage() {
        val currentIndex = (_cartItems.value?.pageIndex) ?: 0
        shoppingCartRepository.getPage(PAGE_SIZE, currentIndex - 1) { page ->
            _cartItems.postValue(page)
        }
    }

    fun requestNextPage() {
        val currentIndex = (_cartItems.value?.pageIndex) ?: 0
        shoppingCartRepository.getPage(PAGE_SIZE, currentIndex + 1) { page ->
            _cartItems.postValue(page)
        }
    }

    fun increaseQuantity(
        position: Int,
        cartItem: CartItem,
    ) {
        val updatedItem = cartItem.copy(quantity = cartItem.quantity + 1)
        (_cartItems.value?.items ?: emptyList()).toMutableList().let { newList ->
            newList[position] = updatedItem
            _cartItems.postValue(_cartItems.value?.copy(items = newList))
        }
        inventoryRepository.insert(updatedItem.toInventoryProduct())
        shoppingCartRepository.insert(updatedItem)
    }

    fun decreaseQuantity(
        position: Int,
        cartItem: CartItem,
    ) {
        if (cartItem.quantity == MINIMUM_CART_ITEM_QUANTITY) {
            removeCartItem(cartItem)
            return
        }
        val updatedItem = cartItem.copy(quantity = cartItem.quantity - 1)
        (_cartItems.value?.items ?: emptyList()).toMutableList().let { newList ->
            newList[position] = updatedItem
            _cartItems.postValue(_cartItems.value?.copy(items = newList))
        }
        inventoryRepository.insert(updatedItem.toInventoryProduct())
        shoppingCartRepository.insert(updatedItem)
    }

    fun removeCartItem(cartItem: CartItem) {
        shoppingCartRepository.delete(cartItem)
        inventoryRepository.insert(cartItem.toInventoryProduct().copy(quantity = 0))
        requestPage(_cartItems.value?.pageIndex ?: 0)
    }

    companion object {
        private const val PAGE_SIZE = 5
        private const val MINIMUM_CART_ITEM_QUANTITY = 1

        @Suppress("UNCHECKED_CAST")
        fun createFactory(
            inventoryRepository: InventoryRepository,
            shoppingCartRepository: ShoppingCartRepository2,
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return (
                        ShoppingCartViewModel(
                            inventoryRepository,
                            shoppingCartRepository,
                        ) as T
                    )
                }
            }
        }
    }
}
