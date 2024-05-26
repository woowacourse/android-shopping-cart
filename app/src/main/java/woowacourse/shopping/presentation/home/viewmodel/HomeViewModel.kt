package woowacourse.shopping.presentation.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.presentation.action.CartItemCountHandler
import woowacourse.shopping.presentation.home.HomeActionHandler
import woowacourse.shopping.presentation.uistate.Order
import woowacourse.shopping.presentation.util.Event

class HomeViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
) : ViewModel(), HomeActionHandler, CartItemCountHandler {
    private val _totalCartCount: MutableLiveData<Int> = MutableLiveData(0)
    val totalCartCount: LiveData<Int>
        get() = _totalCartCount

    private val _orders: MutableLiveData<List<Order>> =
        MutableLiveData<List<Order>>(emptyList())
    val orders: LiveData<List<Order>>
        get() = _orders

    private val _isLoadingAvailable: MutableLiveData<Boolean> = MutableLiveData(true)
    val isLoadingAvailable: LiveData<Boolean>
        get() = _isLoadingAvailable

    private val _onProductClicked = MutableLiveData<Event<Long>>()
    val onProductClicked: LiveData<Event<Long>>
        get() = _onProductClicked

    private val _onCartClicked = MutableLiveData<Event<Unit>>()
    val onCartClicked: LiveData<Event<Unit>>
        get() = _onCartClicked

    private val _updateOrder = MutableLiveData<Order>()
    val updateOrder: LiveData<Order>
        get() = _updateOrder

    init {
        loadProducts()
    }

    fun loadTotalCartCount() {
        _totalCartCount.value = cartRepository.fetchTotalCartCount()
    }

    fun plusCartItem(productId: Long) {
        cartRepository.plusCartItem(productId, 1)

        updateOrder(productId)
    }

    fun minusCartItem(productId: Long) {
        cartRepository.minusCartItem(productId, 1)

        updateOrder(productId)
    }

    fun addCartItem(productId: Long) {
        cartRepository.addCartItem(productId, 1)
        updateOrder(productId)
        loadTotalCartCount()
    }

    fun updateOrder(productId: Long) {
        val cartItem = cartRepository.fetchCartItem(productId)

        cartItem?.let {
            _updateOrder.value =
                orders.value?.find {
                    it.product.id == cartItem.productId
                }?.copy(
                    quantity = cartItem.quantity,
                )
        }
    }

    fun loadProducts() {
        val carts = cartRepository.fetchAllCart()

        _orders.value =
            orders.value?.plus(
                productRepository.fetchNextPage().map { product ->
                    val cart = carts?.find { it.productId == product.id }

                    Order(
                        cartItemId = cart?.id ?: 0,
                        quantity = cart?.quantity ?: 0,
                        product = product,
                    )
                },
            )

        _isLoadingAvailable.value = productRepository.fetchCurrentPage().isNotEmpty()
    }

    override fun onProductItemClick(id: Long) {
        _onProductClicked.value = Event(id)
    }

    override fun onMoveToCart() {
        _onCartClicked.value = Event(Unit)
    }

    override fun onAddCartItem(id: Long) {
        addCartItem(id)
    }

    override fun onCartItemAdd(id: Long) {
        plusCartItem(id)
    }

    override fun onCartItemMinus(id: Long) {
        minusCartItem(id)
    }

    override fun onLoadClick() {
        loadProducts()
    }
}
