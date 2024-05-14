package woowacourse.shopping.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.model.Quantity

class CartViewModel : ViewModel() {
    private val _cart = MutableLiveData<Map<Long, Quantity>>()
    val cart: LiveData<Map<Long, Quantity>> get() = _cart

    fun add(
        cartRepository: CartRepository,
        productId: Long,
    ) {
        cartRepository.add(productId)
    }
}
