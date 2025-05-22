package woowacourse.shopping.feature.goods

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import woowacourse.shopping.data.repository.CartRepository
import woowacourse.shopping.domain.model.Cart
import woowacourse.shopping.domain.model.Goods
import woowacourse.shopping.domain.model.Goods.Companion.dummyGoods
import woowacourse.shopping.util.MutableSingleLiveData
import woowacourse.shopping.util.SingleLiveData
import kotlin.math.min

class GoodsViewModel(
    private val repository: CartRepository,
) : ViewModel() {
    private val _carts = MutableLiveData<List<Cart>>()
    val carts: LiveData<List<Cart>> get() = _carts
    private val _showMoreButton = MutableLiveData(false)
    val showMoreButton: LiveData<Boolean> get() = _showMoreButton
    private val _isFullLoaded = MutableLiveData<Boolean>()
    val isFullLoaded: LiveData<Boolean> get() = _isFullLoaded
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

    fun updateMoreButtonVisibility(shouldShow: Boolean) {
        _showMoreButton.value = shouldShow
    }

    fun insertToCart(cart: Cart) {
        try {
            repository.insert(cart)
            _isSuccess.setValue(Unit)
        } catch (e: Exception) {
            _isFail.setValue(Unit)
        }
    }

    fun removeFromCart(cart: Cart) {
        repository.delete(cart)
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
            val currentList = _carts.value ?: emptyList()
            val newGoods = getProducts(page)

            val existingCarts = repository.getAll()
            val quantityMap = existingCarts.associateBy { it.goods.id }

            val newCarts =
                newGoods.map { goods ->
                    val quantity = quantityMap[goods.id]?.quantity ?: 0
                    Cart(goods = goods, quantity = quantity)
                }

            _carts.postValue(currentList + newCarts)
            _isFullLoaded.postValue((page + 1) * PAGE_SIZE >= dummyGoods.size)
        }
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}
