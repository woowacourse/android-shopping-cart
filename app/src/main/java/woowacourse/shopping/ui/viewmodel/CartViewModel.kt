package woowacourse.shopping.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.domain.product.CartItem
import woowacourse.shopping.domain.product.Product
import kotlin.concurrent.thread

class CartViewModel : ViewModel() {
    private val cartRepository = CartRepository.get()

    private val _cartItems = MutableLiveData<List<CartItem>>(emptyList())
    val cartItems: LiveData<List<CartItem>> = _cartItems

    private val _pageNumber = MutableLiveData(1)
    val pageNumber: LiveData<Int> = _pageNumber

    private val _quantitys = MutableLiveData<List<CartItem>>(emptyList())
    val quantitys: LiveData<List<CartItem>> = _quantitys

    private var size: Int = 0

    init {
        thread {
            size = cartRepository.size()
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
            val items = cartRepository.getPagedItems(5, (pageNumber - 1) * 5)
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
            cartRepository.delete(product.id)
        }
        _cartItems.value = _cartItems.value?.filter { it.id != product.id }
    }

    fun increaseQuantity(cartItem: CartItem) {
        val target = _quantitys.value?.find { it.id == cartItem.id }
        val newCartItem = cartItem.copy(quantity = target?.quantity?.plus(1) ?: 1)
        thread {
            cartRepository.update(newCartItem)
        }
    }

    fun decreaseQuantity(cartItem: CartItem) {
        val target = _quantitys.value?.find { it.id == cartItem.id }
        val newCartItem = cartItem.copy(quantity = target?.quantity?.minus(1)?.coerceAtLeast(1) ?: 1)
        if (target?.quantity != 1) {
            thread {
                cartRepository.update(newCartItem)
            }
        } else {
            thread {
                cartRepository.delete(cartItem.product.id)
            }
        }
    }
}
