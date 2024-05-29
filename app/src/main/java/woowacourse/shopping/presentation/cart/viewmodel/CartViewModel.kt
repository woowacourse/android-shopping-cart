package woowacourse.shopping.presentation.cart.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.presentation.action.CartItemCountHandler
import woowacourse.shopping.presentation.uistate.Order
import woowacourse.shopping.presentation.uistate.PageInformation

class CartViewModel(
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository,
) : ViewModel(), CartItemCountHandler {
    private var _currentPage: MutableLiveData<Int> = MutableLiveData(0)
    val currentPage: LiveData<Int>
        get() = _currentPage

    private val _orders: MutableLiveData<List<Order>> = MutableLiveData(emptyList())
    val orders: LiveData<List<Order>>
        get() = _orders

    private val _pageInformation: MutableLiveData<PageInformation> =
        MutableLiveData(PageInformation())
    val pageInformation: LiveData<PageInformation>
        get() = _pageInformation

    private val _updateOrder: MutableLiveData<Order> = MutableLiveData()
    val updateOrder: LiveData<Order>
        get() = _updateOrder

    private val _productIds: MutableLiveData<List<Long>> = MutableLiveData(mutableListOf())
    val productIds: LiveData<List<Long>>
        get() = _productIds

    private var hasNext: Boolean = true

    init {
        loadCurrentPageCartItems()
    }

    fun loadPreviousPageCartItems() {
        _currentPage.value = currentPage.value?.minus(1)

        loadCurrentPageCartItems()
    }

    fun loadNextPageCartItems() {
        _currentPage.value = currentPage.value?.plus(1)

        loadCurrentPageCartItems()
    }

    fun removeAllCartItem(productId: Long) {
        cartRepository.removeAllCartItem(productId = productId)

        if (orders.value?.size == 1 && currentPage.value != 0) {
            _currentPage.value = currentPage.value?.minus(1)
        }

        loadCurrentPageCartItems()
    }

    fun loadCurrentPageCartItems() {
        val cartItems = cartRepository.fetchCartItems(currentPage.value ?: return, PAGE_SIZE)
        hasNext =
            cartRepository.fetchCartItems(currentPage.value?.plus(1) ?: return, PAGE_SIZE)
                .isNotEmpty()
        val products = productRepository.fetchProducts(cartItems.map { it.productId })

        val orders =
            cartItems.zip(products).map {
                Order(
                    cartItemId = it.first.id,
                    quantity = it.first.quantity,
                    product = it.second,
                )
            }

        _pageInformation.value =
            pageInformation.value?.copy(
                previousPageEnabled = currentPage.value != 0,
                nextPageEnabled = hasNext,
            )
        _orders.value = orders
    }

    override fun onCartItemAdd(id: Long) {
        cartRepository.plusCartItem(id, 1)

        _productIds.value = productIds.value?.plus(id)
        updateOrder(id)
    }

    override fun onCartItemMinus(id: Long) {
        cartRepository.minusCartItem(id, 1)

        _productIds.value = productIds.value?.plus(id)
        updateOrder(id)
    }

    private fun updateOrder(id: Long) {
        val cartItem = cartRepository.fetchCartItem(id)

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

    companion object {
        private const val PAGE_SIZE = 5
    }
}
