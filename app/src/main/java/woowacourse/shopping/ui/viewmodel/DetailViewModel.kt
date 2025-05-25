package woowacourse.shopping.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.domain.product.CartItem
import woowacourse.shopping.domain.product.Product
import kotlin.concurrent.thread

class DetailViewModel(item: Product) : ViewModel() {
    private val cartRepository = CartRepository.get()
    private val _cartItem = MutableLiveData(CartItem(item.id, item, 1))
    val cartItem: LiveData<CartItem> = _cartItem

    private val _quantity = MutableLiveData(1)
    val quantity: LiveData<Int> = _quantity

    private val _lastItem = MutableLiveData<Product>()
    val lastItem: LiveData<Product> = _lastItem

    init {
        loadLastItem()
    }

    fun loadLastItem() {
        thread {
            val recent = cartRepository.getLastRecentProduct() ?: return@thread
            _lastItem.postValue(recent)
        }
    }

    fun insertCartItem(cartItem: CartItem) {
        thread {
            cartRepository.insert(cartItem)
        }
    }

    fun increaseQuantity() {
        _cartItem.value = _cartItem.value?.copy(quantity = _cartItem.value?.quantity?.plus(1) ?: 1)
    }

    fun decreaseQuantity() {
        _cartItem.value = _cartItem.value?.copy(quantity = _cartItem.value?.quantity?.minus(1)?.coerceAtLeast(1) ?: 1)
    }
}
