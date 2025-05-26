package woowacourse.shopping.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.repository.CartItemRepositoryImpl
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.domain.product.CartItem
import woowacourse.shopping.domain.product.Product
import kotlin.concurrent.thread

class CartViewModel : ViewModel() {
    private val cartRepository = CartRepositoryImpl.get()
    private val cartItemRepository = CartItemRepositoryImpl.get()

    private val _cartItems = MutableLiveData<List<CartItem>>(emptyList())
    val cartItems: LiveData<List<CartItem>> = _cartItems

    private val _pageNumber = MutableLiveData(1)
    val pageNumber: LiveData<Int> = _pageNumber

    private var size: Int = 0

    init {
        thread {
            size = cartRepository.getSize()
        }
        update()
    }

    fun isLastPage(): Boolean {
        val pageNumber = pageNumber.value ?: 1
        val size = pageNumber * 5
        return this.size <= size
    }

    private fun update() {
        val pageNumber = pageNumber.value ?: 1
        thread {
            val items = cartItemRepository.getPagedCartItems(5, (pageNumber - 1) * 5)
            _cartItems.postValue(items)
        }
    }

    fun moveToPrevious() {
        if ((_pageNumber.value ?: 1) > 1) {
            _pageNumber.value = _pageNumber.value?.minus(1)
            update()
        }
    }

    fun moveToNext() {
        if (!isLastPage()) {
            _pageNumber.value = _pageNumber.value?.plus(1)
            update()
        }
    }

    fun deleteProduct(product: Product) {
        thread {
            cartRepository.deleteById(product.id)
            cartItemRepository.deleteById(product.id)
        }
        _cartItems.value = _cartItems.value?.filter { it.id != product.id }
    }

    fun increaseQuantity(cartItem: CartItem) {
        val newItem = cartItem.copy(quantity = cartItem.quantity + 1)
        val updatedList =
            _cartItems.value?.map {
                if (it.id == cartItem.id) {
                    newItem
                } else {
                    it
                }
            } ?: return

        _cartItems.value = updatedList

        thread {
            cartItemRepository.update(newItem)
        }
    }

    fun decreaseQuantity(cartItem: CartItem) {
        val target = _cartItems.value?.find { it.id == cartItem.id } ?: return
        val newCartItem = cartItem.copy(quantity = target.quantity.minus(1).coerceAtLeast(1))
        if (target.quantity > 1) {
            val newCartItem = cartItem.copy(quantity = target.quantity - 1)
            val updatedList =
                _cartItems.value?.map {
                    if (it.id == cartItem.id) newCartItem else it
                } ?: return

            _cartItems.value = updatedList

            thread {
                cartItemRepository.update(newCartItem)
            }
        } else {
            val updatedList = _cartItems.value?.filter { it.id != cartItem.id } ?: return
            _cartItems.value = updatedList

            thread {
                cartRepository.deleteById(cartItem.id)
                cartItemRepository.deleteById(cartItem.id)
            }
        }
    }
}
