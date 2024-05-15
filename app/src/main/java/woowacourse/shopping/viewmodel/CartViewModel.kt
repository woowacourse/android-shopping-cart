package woowacourse.shopping.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.model.CartItem

class CartViewModel : ViewModel() {
    private val _cart = MutableLiveData<List<CartItem>>()
    val cart: LiveData<List<CartItem>> get() = _cart

    fun add(
        cartRepository: CartRepository,
        productId: Long,
    ) {
        cartRepository.add(productId)
    }

    fun delete(
        cartRepository: CartRepository,
        productId: Long,
    ) {
        cartRepository.deleteAll(productId)
    }

    fun load(cartRepository: CartRepository) {
        _cart.value = cartRepository.findAll()
    }
}
