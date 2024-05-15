package woowacourse.shopping.presentation.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository

class CartViewModel(
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository,
) : ViewModel() {
    private var _currentPage: MutableLiveData<Int> = MutableLiveData(0)
    val currentPage: LiveData<Int>
        get() = _currentPage

    private val _orders: MutableLiveData<List<Order>> = MutableLiveData(emptyList())
    val orders: LiveData<List<Order>>
        get() = _orders

    private val _pageInformation: MutableLiveData<PageInformation> = MutableLiveData(PageInformation())
    val pageInformation: LiveData<PageInformation>
        get() = _pageInformation

    fun loadPreviousPageCartItems() {
        _currentPage.value = currentPage.value?.minus(1)
        loadCurrentPageCartItems()
    }

    fun loadNextPageCartItems() {
        _currentPage.value = currentPage.value?.plus(1)
        loadCurrentPageCartItems()
    }

    fun removeCartItem(cartItemId: Long) {
        cartRepository.removeCartItem(cartItemId = cartItemId)
        loadCurrentPageCartItems()
    }

    fun loadCurrentPageCartItems() {
        // TODO return 대신 토스트 띄우기
        val cartItems = cartRepository.fetchCartItems(currentPage.value ?: return)
        val orders =
            cartItems.map {
                val productInformation = productRepository.fetchProduct(it.productId)
                Order(
                    cartItemId = it.id,
                    image = productInformation.imageSource,
                    productName = productInformation.name,
                    quantity = it.quantity,
                    price = it.quantity * productInformation.price,
                )
            }
        if (currentPage.value == 0) {
            if (orders.size < 5) {
                _pageInformation.value =
                    pageInformation.value?.copy(
                        previousPageEnabled = false,
                        nextPageEnabled = false,
                    )
            } else {
                _pageInformation.value =
                    pageInformation.value?.copy(
                        previousPageEnabled = false,
                        nextPageEnabled = true,
                    )
            }
        } else {
            if (orders.size < 5) {
                _pageInformation.value =
                    pageInformation.value?.copy(
                        previousPageEnabled = true,
                        nextPageEnabled = false,
                    )
            } else {
                _pageInformation.value =
                    pageInformation.value?.copy(
                        previousPageEnabled = true,
                        nextPageEnabled = true,
                    )
            }
        }
        _orders.value = orders
    }
}

data class PageInformation(
    val previousPageEnabled: Boolean = false,
    val nextPageEnabled: Boolean = false,
)
