package woowacourse.shopping.feature.goods

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.carts.repository.CartRepository
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Goods
import woowacourse.shopping.domain.model.Goods.Companion.dummyGoods
import woowacourse.shopping.util.MutableSingleLiveData
import woowacourse.shopping.util.SingleLiveData
import kotlin.math.min

class GoodsViewModel(
    private val cartRepository: CartRepository,
) : ViewModel() {
    private val goods = mutableListOf<Goods>()
    private var page: Int = 1
    private val _isFullLoaded = MutableLiveData(PAGE_SIZE >= dummyGoods.size)
    val isFullLoaded: LiveData<Boolean> get() = _isFullLoaded
    private val _cartItemsWithZeroQuantity = MutableLiveData<List<CartItem>>()
    val cartItemsWithZeroQuantity: LiveData<List<CartItem>> get() = _cartItemsWithZeroQuantity
    private var _totalCartItemSize: MutableLiveData<String> = MutableLiveData("0")
    val totalCartItemSize: LiveData<String> get() = _totalCartItemSize
    private val _navigateToCart = MutableSingleLiveData<Unit>()
    val navigateToCart: SingleLiveData<Unit> get() = _navigateToCart

    init {
        appendCartItemsWithZeroQuantity()
        updateCartQuantity()
    }

    fun onCartClicked() {
        _navigateToCart.setValue(Unit)
    }

    private fun appendCartItemsWithZeroQuantity() {
        val loadNewGoodsList = getProducts()
        goods.addAll(loadNewGoodsList)
        _isFullLoaded.value = page * PAGE_SIZE >= dummyGoods.size
        _cartItemsWithZeroQuantity.value = goods.map { CartItem(goods = it, quantity = 0) }
    }

    fun updateCartQuantity() {
        cartRepository.fetchAllCartItems { cartItems ->
            val cartItemsMap = cartItems.associateBy { it.goods.id }
            _cartItemsWithZeroQuantity.value =
                goods.map { goods ->
                    cartItemsMap[goods.id] ?: CartItem(goods = goods, quantity = 0)
                }
            setTotalCartItemSize(cartItems.sumOf { it.quantity })
        }
    }

    private fun setTotalCartItemSize(totalCartQuantity: Int) {
        when {
            totalCartQuantity < 1 -> _totalCartItemSize.value = "0"
            totalCartQuantity in 1..99 -> _totalCartItemSize.value = totalCartQuantity.toString()
            else -> _totalCartItemSize.value = "99+"
        }
    }

    fun addPage() {
        page++
        appendCartItemsWithZeroQuantity()
    }

    fun addCartItemOrIncreaseQuantity(cartItem: CartItem) {
        cartRepository.addOrIncreaseQuantity(cartItem.goods, cartItem.quantity) {
            updateCartQuantity()
        }
    }

    fun removeCartItemOrDecreaseQuantity(cartItem: CartItem) {
        cartRepository.removeOrDecreaseQuantity(cartItem.goods, cartItem.quantity) {
            updateCartQuantity()
        }
    }

    private fun getProducts(pageSize: Int = PAGE_SIZE): List<Goods> {
        val fromIndex = (page - 1) * pageSize
        val toIndex = min(page * pageSize, dummyGoods.size)
        return dummyGoods.subList(fromIndex, toIndex)
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}
