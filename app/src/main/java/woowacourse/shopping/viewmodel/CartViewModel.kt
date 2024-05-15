package woowacourse.shopping.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.model.CartItem

class CartViewModel(private val cartRepository: CartRepository) : ViewModel() {
    private val _cart = MutableLiveData<List<CartItem>>()
    val cart: LiveData<List<CartItem>> get() = _cart

    private val _cartSize = MutableLiveData<Int>()
    val cartSize: LiveData<Int> get() = _cartSize

    fun add(productId: Long) {
        cartRepository.increaseQuantity(productId)
    }

    fun delete(productId: Long) {
        cartRepository.deleteCartItem(productId)
    }

    fun loadCart(
        page: Int,
        pageSize: Int,
    ) {
        _cart.value = cartRepository.findRange(page, pageSize)
    }

    fun loadCount() {
        _cartSize.value = cartRepository.count()
    }
}
