package woowacourse.shopping.view.shoppingcart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.inventory.InventoryRepository
import woowacourse.shopping.data.shoppingcart.ShoppingCartRepository2
import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.domain.Page

class ShoppingCartViewModel(
    private val inventoryRepository: InventoryRepository,
    private val shoppingCartRepository: ShoppingCartRepository2,
) : ViewModel() {
    private val _cartItems = MutableLiveData<Page<CartItem>>()
    val cartItems: LiveData<Page<CartItem>> get() = _cartItems

    fun removeCartItem(cartItem: CartItem) {
        shoppingCartRepository.getAll { allItems ->
            val removedItemIndex = allItems.indexOf(cartItem)
            val pageNumber = pageNumberAfterRemoval(allItems.size, removedItemIndex)
            requestPage(pageNumber)
        }
    }

    fun requestPage(pageIndex: Int) {
        shoppingCartRepository.getPage(PAGE_SIZE, pageIndex) { page ->
            _cartItems.postValue(page)
        }
    }

    fun requestPreviousPage() {
        val currentIndex = (_cartItems.value?.pageIndex) ?: 0
        val previousIndex = (currentIndex - 1).coerceAtLeast(0)
        shoppingCartRepository.getPage(
            PAGE_SIZE,
            previousIndex,
        ) { page -> _cartItems.postValue(page) }
    }

    fun requestNextPage() {
        val currentIndex = (_cartItems.value?.pageIndex) ?: 0
        val nextIndex = currentIndex + 1
        shoppingCartRepository.getPage(PAGE_SIZE, nextIndex) { page -> _cartItems.postValue(page) }
    }

    private fun pageNumberAfterRemoval(
        itemsCount: Int,
        removedIndex: Int,
    ): Int {
        val currentPageNumber = removedIndex / PAGE_SIZE
        val newPageNumber =
            if (itemsCount % PAGE_SIZE == 0 && removedIndex == itemsCount) {
                currentPageNumber - 1
            } else {
                currentPageNumber
            }
        return newPageNumber.coerceAtLeast(0)
    }

    companion object {
        private const val PAGE_SIZE = 5

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
