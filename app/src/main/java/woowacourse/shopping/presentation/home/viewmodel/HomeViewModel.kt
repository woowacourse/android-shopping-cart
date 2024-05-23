package woowacourse.shopping.presentation.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.presentation.action.CartItemCountHandler
import woowacourse.shopping.presentation.home.HomeActionHandler
import woowacourse.shopping.presentation.uistate.LoadStatus
import woowacourse.shopping.presentation.uistate.Order
import woowacourse.shopping.util.Event

class HomeViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
) : ViewModel(), HomeActionHandler, CartItemCountHandler {
    private var page: Int = 0

    private val _totalCartCount: MutableLiveData<Int> = MutableLiveData(0)
    val totalCartCount: LiveData<Int>
        get() = _totalCartCount

    private val _orders: MutableLiveData<List<Order>> =
        MutableLiveData<List<Order>>(emptyList())
    val orders: LiveData<List<Order>>
        get() = _orders

    private val _loadStatus: MutableLiveData<LoadStatus> = MutableLiveData(LoadStatus())
    val loadStatus: LiveData<LoadStatus>
        get() = _loadStatus

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
                    cartItemId = cartItem.id,
                    quantity = cartItem.quantity,
                )
        }
    }

    fun loadProducts() {
        _loadStatus.value = loadStatus.value?.copy(isLoadingPage = true, loadingAvailable = false)

        _orders.value =
            orders.value?.plus(
                productRepository.fetchSinglePage(page++).map {
                    val cartItem = cartRepository.fetchCartItem(it.id)

                    Order(
                        cartItemId = cartItem?.id ?: 0,
                        quantity = cartItem?.quantity ?: 0,
                        product = it,
                    )
                },
            )

        _loadStatus.value =
            loadStatus.value?.copy(
                loadingAvailable = productRepository.fetchSinglePage(page).isNotEmpty(),
                isLoadingPage = false,
            )
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
