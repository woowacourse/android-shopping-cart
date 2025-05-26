package woowacourse.shopping.feature.goods

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import woowacourse.shopping.data.cart.repository.CartRepository
import woowacourse.shopping.data.history.repository.HistoryRepository
import woowacourse.shopping.domain.model.Cart
import woowacourse.shopping.domain.model.Goods
import woowacourse.shopping.domain.model.Goods.Companion.dummyGoods
import woowacourse.shopping.util.MutableSingleLiveData
import woowacourse.shopping.util.SingleLiveData
import woowacourse.shopping.util.updateCartQuantity
import kotlin.math.min

class GoodsViewModel(
    private val cartRepository: CartRepository,
    private val historyRepository: HistoryRepository,
) : ViewModel() {
    private val _items = MutableLiveData<List<Any>>()
    val items: LiveData<List<Any>> get() = _items
    private val _totalQuantity = MutableLiveData(0)
    val totalQuantity: LiveData<Int> get() = _totalQuantity
    private val _hasNextPage = MutableLiveData(true)
    val hasNextPage: LiveData<Boolean> get() = _hasNextPage
    private val _navigateToCart = MutableSingleLiveData<Cart>()
    val navigateToCart: SingleLiveData<Cart> get() = _navigateToCart
    private val _isSuccess = MutableSingleLiveData<Unit>()
    val isSuccess: SingleLiveData<Unit> get() = _isSuccess
    private val _isFail = MutableSingleLiveData<Unit>()
    val isFail: SingleLiveData<Unit> get() = _isFail
    private var page: Int = INITIAL_PAGE

    init {
        loadItems()
    }

    fun addPage() {
        page++
        loadItems()
    }

    fun insertToCart(cart: Cart) {
        viewModelScope.launch {
            try {
                cartRepository.insert(cart)
                updateItemsAndTotalQuantity(cart, cart.quantity + 1)
                _isSuccess.setValue(Unit)
            } catch (e: Exception) {
                _isFail.setValue(Unit)
            }
        }
    }

    fun removeFromCart(cart: Cart) {
        viewModelScope.launch {
            cartRepository.delete(cart)
            updateItemsAndTotalQuantity(cart, cart.quantity - 1)
        }
    }

    fun refreshHistoryOnly() {
        historyRepository.getAll { histories ->
            val currentItems = _items.value.orEmpty().toMutableList()

            val updatedItems =
                if (currentItems.firstOrNull() is List<*>) {
                    val cartsOnly = currentItems.drop(1)
                    listOf(histories) + cartsOnly
                } else {
                    listOf(histories) + currentItems
                }

            _items.postValue(updatedItems)
        }
        Log.d("123451", "dd")
    }

    fun findCartFromHistory(cart: Cart) {
        val cart =
            _items.value
                ?.filterIsInstance<Cart>()
                ?.find { it.goods.id == cart.goods.id }
        if (cart != null) {
            _navigateToCart.setValue(cart)
        }
    }

    private fun updateItemsAndTotalQuantity(
        updatedCart: Cart,
        newQuantity: Int,
    ) {
        val updatedItems = _items.value?.updateCartQuantity(updatedCart.goods.id, newQuantity) ?: listOf(updatedCart)
        _items.value = updatedItems
        val total = updatedItems.filterIsInstance<Cart>().sumOf { it.quantity }
        _totalQuantity.value = total
    }

    private fun getProducts(
        page: Int,
        pageSize: Int = PAGE_SIZE,
    ): List<Goods> {
        val fromIndex = page * pageSize
        val toIndex = min(fromIndex + pageSize, dummyGoods.size)
        return dummyGoods.subList(fromIndex, toIndex)
    }

    private fun loadItems() {
        val currentItems = _items.value.orEmpty()

        historyRepository.getAll { histories ->
            cartRepository.getAll { cartsResult ->
                val newGoods = getProducts(page)

                val hasMore = (page + 1) * PAGE_SIZE < dummyGoods.size
                _hasNextPage.postValue(hasMore)

                val updatedCarts =
                    newGoods.map { goods ->
                        val quantity = cartsResult.carts.find { it.goods.id == goods.id }?.quantity ?: 0
                        Cart(goods = goods, quantity = quantity)
                    }

                val combinedItems: MutableList<Any> = mutableListOf()
                if (page == INITIAL_PAGE) combinedItems.add(histories)
                combinedItems.addAll(updatedCarts)

                _items.postValue(currentItems + combinedItems)

                val total = combinedItems.filterIsInstance<Cart>().sumOf { it.quantity }
                _totalQuantity.postValue(total)
            }
        }
    }

    fun updateItemQuantity(
        id: Long,
        quantity: Int,
    ) {
        val currentItems = _items.value.orEmpty().toMutableList()

        val index = currentItems.indexOfFirst { it is Cart && it.goods.id == id }

        if (index != -1) {
            val oldItem = currentItems[index] as Cart
            val updatedItem = oldItem.copy(quantity = quantity)

            currentItems[index] = updatedItem
            _items.value = currentItems

            val total = currentItems.filterIsInstance<Cart>().sumOf { it.quantity }
            _totalQuantity.value = total
        }
    }

    companion object {
        private const val PAGE_SIZE = 20
        private const val INITIAL_PAGE = 0
    }
}
