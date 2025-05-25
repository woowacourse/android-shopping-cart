package woowacourse.shopping.feature.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.carts.repository.CartRepository
import woowacourse.shopping.domain.model.CartItem
import kotlin.math.max

class CartViewModel(
    private val cartRepository: CartRepository,
) : ViewModel() {
    private val _isMultiplePages = MutableLiveData(false)
    val isMultiplePages: LiveData<Boolean> get() = _isMultiplePages

    private var currentPage: Int = 1
        set(value) {
            field = value
            _page.postValue(value)
        }
    private val _page = MutableLiveData(currentPage)
    val page: LiveData<Int> get() = _page

    private val _cart = MutableLiveData<List<CartItem>>()
    val cart: LiveData<List<CartItem>> get() = _cart

    private var totalCartSizeData: Int = 0

    private val _isLeftPageEnable = MutableLiveData(false)
    val isLeftPageEnable: LiveData<Boolean> get() = _isLeftPageEnable

    private val _isRightPageEnable = MutableLiveData(false)
    val isRightPageEnable: LiveData<Boolean> get() = _isRightPageEnable

    private val endPage: Int get() = max(1, (totalCartSizeData + PAGE_SIZE - 1) / PAGE_SIZE)

    init {
        updateCartQuantity()
    }

    fun getPosition(cartItem: CartItem): Int? {
        val idx = cart.value?.indexOf(cartItem) ?: return null
        return if (idx >= 0) idx else null
    }

    fun addCartItemOrIncreaseQuantity(cartItem: CartItem) {
        cartRepository.addOrIncreaseQuantity(cartItem.goods, cartItem.quantity) {
            updateCartQuantity()
        }
    }

    fun removeCartItemOrDecreaseQuantity(cartItem: CartItem) {
        cartRepository.removeOrDecreaseQuantity(cartItem.goods, cartItem.quantity) {
            updateCartQuantity()
            updatePage()
        }
    }

    fun updateCartQuantity() {
        cartRepository.fetchPageCartItems(
            PAGE_SIZE,
            (currentPage - 1) * PAGE_SIZE,
        ) { currentPageCartItems ->
            _cart.value = currentPageCartItems
        }
        updateCartDataSize()
        updatePageMoveAvailability()
    }

    private fun updateCartData() {
        cartRepository.fetchPageCartItems(
            PAGE_SIZE,
            (currentPage - 1) * PAGE_SIZE,
        ) { currentPageCartItems ->
            _cart.postValue(currentPageCartItems)
        }
    }

    private fun updateCartDataSize() {
        cartRepository.getAllItemsSize { totalCartSize ->
            totalCartSizeData = totalCartSize
            _isMultiplePages.postValue(totalCartSizeData > PAGE_SIZE)
            updatePageMoveAvailability()
        }
    }

    fun delete(cartItem: CartItem) {
        cartRepository.delete(cartItem.goods) {
            updatePage()
        }
    }

    private fun updatePage() {
        cartRepository.getAllItemsSize { totalCartSize ->
            totalCartSizeData = totalCartSize
            if (currentPage > endPage) {
                currentPage = endPage
            }
            _isMultiplePages.postValue(totalCartSize > PAGE_SIZE)
            updatePageMoveAvailability()
            updateCartData()
        }
    }

    fun plusPage() {
        currentPage++
        updateCartData()
        updatePageMoveAvailability()
    }

    fun minusPage() {
        currentPage--
        updateCartData()
        updatePageMoveAvailability()
    }

    private fun updatePageMoveAvailability() {
        _isLeftPageEnable.postValue(currentPage > 1)
        _isRightPageEnable.postValue(currentPage < endPage)
    }

    companion object {
        private const val PAGE_SIZE = 5
    }
}
