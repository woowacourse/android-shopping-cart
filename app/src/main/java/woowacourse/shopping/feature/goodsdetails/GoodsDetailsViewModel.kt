package woowacourse.shopping.feature.goodsdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.repository.CartRepository
import woowacourse.shopping.domain.model.Cart
import woowacourse.shopping.util.MutableSingleLiveData
import woowacourse.shopping.util.SingleLiveData
import woowacourse.shopping.util.updateQuantity

class GoodsDetailsViewModel(
    private val repository: CartRepository,
) : ViewModel() {
    private val _cart = MutableLiveData<Cart>()
    val cart: LiveData<Cart> get() = _cart
    private val _isSuccess = MutableSingleLiveData<Unit>()
    val isSuccess: SingleLiveData<Unit> get() = _isSuccess
    private val _isFail = MutableSingleLiveData<Unit>()
    val isFail: SingleLiveData<Unit> get() = _isFail

    fun setInitialCart(cart: Cart) {
        _cart.value = cart
    }

    fun insertToCart(cart: Cart) {
        try {
            val updated = cart.updateQuantity(cart.quantity + 1)
            repository.insert(updated)
            _cart.value = updated
            _isSuccess.setValue(Unit)
        } catch (e: Exception) {
            _isFail.setValue(Unit)
        }
    }
}
