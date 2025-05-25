package woowacourse.shopping.feature.goods

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.carts.repository.CartRepository
import woowacourse.shopping.data.goods.repository.GoodsRepository
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Goods
import woowacourse.shopping.util.MutableSingleLiveData
import woowacourse.shopping.util.SingleLiveData

class GoodsViewModel(
    private val cartRepository: CartRepository,
    private val goodsRepository: GoodsRepository,
) : ViewModel() {
    private val goods = mutableListOf<Goods>()
    private var page: Int = 1
    private val _isFullLoaded = MutableLiveData(false)
    val isFullLoaded: LiveData<Boolean> get() = _isFullLoaded
    private val _goodsWithCartQuantity = MutableLiveData<List<CartItem>>()
    val goodsWithCartQuantity: LiveData<List<CartItem>> get() = _goodsWithCartQuantity
    private var _totalCartItemSize: MutableLiveData<String> = MutableLiveData("0")
    val totalCartItemSize: LiveData<String> get() = _totalCartItemSize
    private val _navigateToCart = MutableSingleLiveData<Unit>()
    val navigateToCart: SingleLiveData<Unit> get() = _navigateToCart
    private val _recentlyViewedGoods: MutableLiveData<List<Goods>> = MutableLiveData()
    val recentlyViewedGoods: LiveData<List<Goods>> get() = _recentlyViewedGoods

    init {
        appendCartItemsWithZeroQuantity()
        updateRecentlyViewedGoods()
    }

    fun onCartClicked() {
        _navigateToCart.setValue(Unit)
    }

    private fun appendCartItemsWithZeroQuantity() {
        val goodsLoadOffset = (page - 1) * PAGE_SIZE
        goodsRepository.fetchPageGoods(
            limit = PAGE_SIZE,
            offset = goodsLoadOffset,
        ) { fetchedGoods ->
            goods.addAll(fetchedGoods)
            goodsRepository.fetchGoodsSize { totalSize ->
                _isFullLoaded.postValue(page * PAGE_SIZE >= totalSize)
            }
            _goodsWithCartQuantity.postValue(goods.map { CartItem(goods = it, quantity = 0) })
        }
    }

    fun updateRecentlyViewedGoods() {
        goodsRepository.fetchRecentGoods { goods ->
            _recentlyViewedGoods.postValue(goods)
        }
    }

    fun updateCartQuantity() {
        cartRepository.fetchAllCartItems { cartItems ->
            val cartItemsMap = cartItems.associateBy { it.goods.id }
            _goodsWithCartQuantity.value =
                goods.map { goods ->
                    cartItemsMap[goods.id] ?: CartItem(goods = goods, quantity = 0)
                }
            setTotalCartItemSize(cartItems.sumOf { it.quantity })
        }
    }

    private fun setTotalCartItemSize(totalCartQuantity: Int) {
        val sizeText =
            when {
                totalCartQuantity < 1 -> "0"
                totalCartQuantity in 1..99 -> totalCartQuantity.toString()
                else -> "99+"
            }
        _totalCartItemSize.postValue(sizeText)
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

    companion object {
        private const val PAGE_SIZE = 20
    }
}
