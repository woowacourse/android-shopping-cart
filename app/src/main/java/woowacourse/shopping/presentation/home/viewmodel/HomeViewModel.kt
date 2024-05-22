package woowacourse.shopping.presentation.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.presentation.cart.Order
import woowacourse.shopping.presentation.home.LoadStatus

class HomeViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
) : ViewModel() {
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

    init {
        loadProducts()
    }

    fun loadTotalCartCount() {
        _totalCartCount.value = cartRepository.fetchTotalCartCount()
    }

    fun addCartItem(productId: Long) {
        cartRepository.addCartItem(productId, 1)

        val cartItem = cartRepository.fetchCartItem(productId)

        _orders.value =
            _orders.value?.map {
                if (it.product.id == cartItem?.productId) {
                    it.copy(
                        cartItemId = cartItem.id,
                        quantity = cartItem.quantity,
                    )
                } else {
                    it
                }
            }

        loadTotalCartCount()
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
}
