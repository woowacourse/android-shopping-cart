package woowacourse.shopping.view.detail.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.repository.CartRepository

class DetailViewModel(
    private val cartRepository: CartRepository,
) : ViewModel() {
    private val _cart = MutableLiveData<Cart>()
    val cart: LiveData<Cart> = _cart

    fun load(productId: Long) {
        cartRepository.getById(productId) { cart ->
            _cart.postValue(cart ?: return@getById)
        }
    }

    fun insertCart() {
        cartRepository.insert(cart.value ?: return) {
        }
    }

    fun plusCartQuantity(cart: Cart) {
        val updated = cart.increase()
        cartRepository.update(updated) {
            _cart.postValue(updated)
        }
    }

    fun minusCartQuantity(cart: Cart) {
        val updated = cart.decrease()
        cartRepository.update(updated) {
            if (updated.quantity == 0) {
                cartRepository.deleteById(cart.product.id) {
                    _cart.postValue(updated)
                }
                return@update
            }
            _cart.postValue(updated)
        }
    }
}
