package woowacourse.shopping.feature.goods

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import woowacourse.shopping.data.cart.repository.CartRepository
import woowacourse.shopping.data.history.repository.HistoryRepository
import woowacourse.shopping.domain.model.Cart
import woowacourse.shopping.domain.model.Goods
import woowacourse.shopping.domain.model.Goods.Companion.dummyGoods
import woowacourse.shopping.domain.model.History
import woowacourse.shopping.feature.model.GoodsItem
import woowacourse.shopping.feature.model.State
import woowacourse.shopping.util.Event
import woowacourse.shopping.util.MutableSingleLiveData
import woowacourse.shopping.util.SingleLiveData
import woowacourse.shopping.util.updateCartQuantity
import kotlin.math.min

class GoodsViewModel(
    private val cartRepository: CartRepository,
    private val historyRepository: HistoryRepository,
) : ViewModel() {
    private val _items = MediatorLiveData<List<GoodsItem>>()
    val items: LiveData<List<GoodsItem>> get() = _items

    private val histories = MutableLiveData<List<History>>()

    private val carts = MutableLiveData<List<Cart>>()

    private val _totalQuantity = MutableLiveData(0)
    val totalQuantity: LiveData<Int> get() = _totalQuantity

    private val _hasNextPage = MutableLiveData(true)
    val hasNextPage: LiveData<Boolean> get() = _hasNextPage

    private val _navigateToCart = MutableSingleLiveData<Cart>()
    val navigateToCart: SingleLiveData<Cart> get() = _navigateToCart

    private val _cartInsertEvent = MutableLiveData<Event<State>>()
    val cartInsertEvent: LiveData<Event<State>> get() = _cartInsertEvent

    private var page: Int = INITIAL_PAGE

    init {
        loadHistories()
        loadCarts()
        _items.addSource(histories) { updateCombinedItems() }
    }

    fun addPage() {
        page++
        updateCombinedItems()
    }

    fun insertToCart(cart: Cart) {
        viewModelScope.launch {
            try {
                cartRepository.insert(cart)
                updateItemsAndTotalQuantity(cart, cart.quantity + 1)
                _cartInsertEvent.value = Event(State.Success)
            } catch (e: Exception) {
                _cartInsertEvent.value = Event(State.Failure)
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
        historyRepository.getAll { historiesList ->
            val currentItems = _items.value.orEmpty().toMutableList()
            val cartsOnly = currentItems.filterIsInstance<GoodsItem.Product>()
            val updatedItems = mutableListOf<GoodsItem>()
            if (historiesList.isNotEmpty()) {
                updatedItems.add(GoodsItem.Recent(historiesList))
            }
            updatedItems.addAll(cartsOnly)
            _items.postValue(updatedItems)
        }
    }

    fun findCartFromHistory(history: History) {
        val product = _items.value?.filterIsInstance<GoodsItem.Product>()?.find { it.cart.goods.id == history.id }
        if (product != null) {
            _navigateToCart.setValue(product.cart)
        }
    }

    fun updateItemQuantity(
        id: Long,
        quantity: Int,
    ) {
        val currentItems = _items.value.orEmpty().toMutableList()

        val index = currentItems.indexOfFirst { it is GoodsItem.Product && it.cart.goods.id == id }

        if (index != -1) {
            val oldItem = currentItems[index] as GoodsItem.Product
            val updatedItem = oldItem.copy(cart = oldItem.cart.copy(quantity = quantity))

            currentItems[index] = updatedItem
            _items.value = currentItems

            val total = currentItems.filterIsInstance<GoodsItem.Product>().sumOf { it.cart.quantity }
            _totalQuantity.value = total
        }
    }

    private fun updateItemsAndTotalQuantity(
        updatedCart: Cart,
        newQuantity: Int,
    ) {
        val updatedItems =
            _items.value
                ?.filterIsInstance<GoodsItem.Recent>()
                ?.plus(
                    _items.value
                        ?.filterIsInstance<GoodsItem.Product>()
                        ?.updateCartQuantity(updatedCart.goods.id, newQuantity)
                        ?: emptyList(),
                )
                ?: listOf(GoodsItem.Product(updatedCart.copy(quantity = newQuantity)))

        _items.value = updatedItems
        _totalQuantity.value = updatedItems.filterIsInstance<GoodsItem.Product>().sumOf { it.cart.quantity }
    }

    private fun getProducts(
        page: Int,
        pageSize: Int = PAGE_SIZE,
    ): List<Goods> {
        val fromIndex = page * pageSize
        val toIndex = min(fromIndex + pageSize, dummyGoods.size)
        return dummyGoods.subList(fromIndex, toIndex)
    }

    private fun updateCombinedItems() {
        val previousItems = _items.value.orEmpty()
        val existingRecent = previousItems.find { it is GoodsItem.Recent } as? GoodsItem.Recent
        val existingProducts = previousItems.filterIsInstance<GoodsItem.Product>()

        val newProducts =
            getProducts(page).map { goods ->
                val quantity = carts.value?.find { it.goods.id == goods.id }?.quantity ?: 0
                GoodsItem.Product(Cart(goods = goods, quantity = quantity))
            }

        val combinedItems =
            buildList {
                if (existingRecent != null) {
                    add(existingRecent)
                } else if (page == INITIAL_PAGE) {
                    histories.value?.let { if (it.isNotEmpty()) add(GoodsItem.Recent(it)) }
                }
                addAll(existingProducts)
                addAll(newProducts)
            }

        _items.value = combinedItems
        _totalQuantity.postValue(combinedItems.filterIsInstance<GoodsItem.Product>().sumOf { it.cart.quantity })
        _hasNextPage.postValue((page + 1) * PAGE_SIZE < dummyGoods.size)
    }

    private fun loadCarts() {
        cartRepository.getAll { cartsResult ->
            carts.postValue(cartsResult.carts)
        }
    }

    private fun loadHistories() {
        historyRepository.getAll { historiesResult ->
            histories.postValue(historiesResult)
        }
    }

    companion object {
        private const val PAGE_SIZE = 20
        private const val INITIAL_PAGE = 0
    }
}
