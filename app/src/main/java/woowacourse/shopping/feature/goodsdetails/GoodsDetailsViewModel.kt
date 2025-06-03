package woowacourse.shopping.feature.goodsdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.cart.repository.CartRepository
import woowacourse.shopping.data.goods.repository.FakeGoodsRepository
import woowacourse.shopping.data.history.repository.HistoryRepository
import woowacourse.shopping.domain.model.Cart
import woowacourse.shopping.domain.model.Goods
import woowacourse.shopping.domain.model.History
import woowacourse.shopping.feature.model.State
import woowacourse.shopping.util.Event
import woowacourse.shopping.util.MutableSingleLiveData
import woowacourse.shopping.util.SingleLiveData
import woowacourse.shopping.util.updateQuantity

class GoodsDetailsViewModel(
    private val cartRepository: CartRepository,
    private val historyRepository: HistoryRepository,
    private val fakeGoodsRepository: FakeGoodsRepository,
) : ViewModel() {
    private val _cart = MutableLiveData<Cart>()
    val cart: LiveData<Cart> get() = _cart

    private val _lastViewed = MutableLiveData<History>()
    val lastViewed: LiveData<History> get() = _lastViewed

    private val _isLastViewedVisible = MutableLiveData<Boolean>()
    val isLastViewedVisible: LiveData<Boolean> get() = _isLastViewedVisible

    private val _cartInsertEvent = MutableLiveData<Event<State>>()
    val cartInsertEvent: LiveData<Event<State>> get() = _cartInsertEvent

    private val _navigateToLastViewedCart = MutableSingleLiveData<Cart>()
    val navigateToLastViewedCart: SingleLiveData<Cart> get() = _navigateToLastViewedCart

    fun setInitialCart(cartId: Long) {
        getCartById(cartId.toInt()) { cart ->
            if (cart != null) {
                _cart.postValue(cart)
                insertToHistory(cart)
            } else {
                getGoodsById(cartId) { goods ->
                    if (goods != null) {
                        val cart = Cart(goods = goods, quantity = 0)
                        _cart.postValue(cart)
                        insertToHistory(cart)
                    }
                }
            }
        }
        loadLastViewed()
    }

    fun increaseQuantity() {
        val current = _cart.value
        if (current != null) {
            val updated = current.updateQuantity(current.quantity + 1)
            _cart.value = updated
        }
    }

    fun decreaseQuantity() {
        val current = _cart.value
        if (current != null) {
            val updated = current.updateQuantity(current.quantity - 1)
            _cart.value = updated
        }
    }

    fun commitCart() {
        runCatching {
            _cart.value?.let {
                cartRepository.insertAll(it)
            }
        }.onSuccess {
            _cartInsertEvent.value = Event(State.Success)
        }.onFailure {
            _cartInsertEvent.value = Event(State.Failure)
        }
    }

    fun updateLastViewedVisibility() {
        val lastName = _lastViewed.value?.name
        val currentName = cart.value?.goods?.name
        _isLastViewedVisible.postValue(lastName != null && currentName != null && lastName != currentName)
    }

    fun emitLastViewedCart() {
        val history = _lastViewed.value
        if (history != null) {
            getCartById(history.id.toInt()) { cart ->
                if (cart != null) {
                    _navigateToLastViewedCart.postValue(cart)
                } else {
                    getGoodsById(history.id) { goods ->
                        if (goods != null) _navigateToLastViewedCart.postValue(Cart(goods = goods, quantity = 0))
                    }
                }
            }
        }
    }

    private fun getGoodsById(
        goodsId: Long,
        onGoodsLoaded: (Goods?) -> Unit,
    ) {
        fakeGoodsRepository.getGoodsById(goodsId) { onGoodsLoaded(it) }
    }

    private fun getCartById(
        cartId: Int,
        onCartLoaded: (Cart?) -> Unit,
    ) {
        cartRepository.getCartById(cartId) { onCartLoaded(it) }
    }

    private fun loadLastViewed() {
        historyRepository.findLatest { lastViewed ->
            if (lastViewed != null) {
                _lastViewed.postValue(lastViewed)
                updateLastViewedVisibility()
            }
        }
    }

    private fun insertToHistory(cart: Cart) {
        historyRepository.insert(History(cart.goods.id, cart.goods.name, cart.goods.thumbnailUrl))
    }
}
