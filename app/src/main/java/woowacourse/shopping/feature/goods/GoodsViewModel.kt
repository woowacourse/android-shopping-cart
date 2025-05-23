package woowacourse.shopping.feature.goods

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import woowacourse.shopping.data.cart.repository.CartRepository
import woowacourse.shopping.domain.model.Cart
import woowacourse.shopping.domain.model.Carts
import woowacourse.shopping.domain.model.Goods
import woowacourse.shopping.domain.model.Goods.Companion.dummyGoods
import woowacourse.shopping.util.MutableSingleLiveData
import woowacourse.shopping.util.SingleLiveData
import woowacourse.shopping.util.updateQuantity
import kotlin.math.min

class GoodsViewModel(
    private val repository: CartRepository,
) : ViewModel() {
    private val _carts = MutableLiveData<Carts>()
    val carts: LiveData<Carts> get() = _carts
    private val _isSuccess = MutableSingleLiveData<Unit>()
    val isSuccess: SingleLiveData<Unit> get() = _isSuccess
    private val _isFail = MutableSingleLiveData<Unit>()
    val isFail: SingleLiveData<Unit> get() = _isFail
    private var page: Int = 0

    init {
        loadGoods()
    }

    fun addPage() {
        page++
        loadGoods()
    }

    fun insertToCart(cart: Cart) {
        try {
            repository.insert(cart)
            _carts.value = _carts.value?.updateQuantity(cart.goods.id, cart.quantity + 1)
            _isSuccess.setValue(Unit)
        } catch (e: Exception) {
            _isFail.setValue(Unit)
        }
    }

    fun removeFromCart(cart: Cart) {
        repository.delete(cart)
        _carts.value = _carts.value?.updateQuantity(cart.goods.id, cart.quantity - 1)
    }

    private fun getProducts(
        page: Int,
        pageSize: Int = PAGE_SIZE,
    ): List<Goods> {
        val fromIndex = page * pageSize
        val toIndex = min(fromIndex + pageSize, dummyGoods.size)
        return dummyGoods.subList(fromIndex, toIndex)
    }

    private fun loadGoods() {
        viewModelScope.launch {
            val newGoods = getProducts(page)

            val existingCarts = repository.getAll()

            val updatedCarts =
                newGoods.map { goods ->
                    val quantity = existingCarts.carts.find { it.goods.id == goods.id }?.quantity ?: 0
                    Cart(goods = goods, quantity = quantity)
                }

            val newCarts = (carts.value?.carts ?: emptyList()) + updatedCarts

            val totalQuantity = newCarts.sumOf { it.quantity }
            _carts.postValue(Carts(newCarts, totalQuantity))
        }
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}
