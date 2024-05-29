package woowacourse.shopping.feature.cart.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.model.CartItem
import kotlin.concurrent.thread

class CartViewModel(
    private val cartRepository: CartRepository,
) : ViewModel() {
    private val _cart = MutableLiveData<List<CartItem>>()
    val cart: LiveData<List<CartItem>> get() = _cart

    private val _cartSize = MutableLiveData<Int>()
    val cartSize: LiveData<Int> get() = _cartSize

    private val _currentPage = MutableLiveData(MIN_PAGE)
    val currentPage: LiveData<Int> = _currentPage

    private val _hasPreviousPage = MutableLiveData<Boolean>()
    val hasPreviousPage: LiveData<Boolean> = _hasPreviousPage

    private val _hasNextPage = MutableLiveData<Boolean>()
    val hasNextPage: LiveData<Boolean> = _hasNextPage

    private val _isEmptyLastPage = MutableLiveData<Boolean>()
    val isEmptyLastPage: LiveData<Boolean> = _isEmptyLastPage

    private val _isOnlyOnePage = MutableLiveData<Boolean>()
    val isOnlyOnePage: LiveData<Boolean> = _isOnlyOnePage

    init {
        updatePage()
    }

    fun deleteCartItem(productId: Long) {
        checkEmptyLastPage()
        val isEmptyLastPage = isEmptyLastPage.value ?: return
        if (isEmptyLastPage) decreasePage()
        thread {
            cartRepository.deleteCartItem(productId)
        }.join()
        updatePage()
    }

    fun loadCart(pageSize: Int) {
        val page = currentPage.value ?: return
        var cart = emptyList<CartItem>()
        thread {
            cart = cartRepository.findRange(page, pageSize).sortedBy { it.product.id }
        }.join()
        _cart.value = cart
    }

    fun loadCount() {
        var cartSize = 0
        thread {
            cartSize = cartRepository.count()
        }.join()
        _cartSize.value = cartSize
        updatePageStatus()
    }

    fun increasePage() {
        _currentPage.value = _currentPage.value?.plus(1)
        updatePage()
    }

    fun decreasePage() {
        _currentPage.value = _currentPage.value?.minus(1)
        updatePage()
    }

    fun addProduct(productId: Long) {
        thread {
            cartRepository.addProduct(productId)
        }.join()
        updatePage()
    }

    fun deleteProduct(productId: Long) {
        thread {
            cartRepository.deleteProduct(productId)
        }.join()
        updatePage()
    }

    private fun checkEmptyLastPage() {
        val currentPage = currentPage.value ?: return
        val cartSize = cartSize.value ?: return
        _isEmptyLastPage.value = currentPage > MIN_PAGE && cartSize % MAX_ITEM_SIZE_PER_PAGE == 1
    }

    private fun updatePageStatus() {
        val currentPage = currentPage.value ?: return
        val cartSize = cartSize.value ?: return

        _isOnlyOnePage.value = cartSize <= MAX_ITEM_SIZE_PER_PAGE
        _hasPreviousPage.value = currentPage > MIN_PAGE
        _hasNextPage.value = currentPage < (cartSize - 1) / MAX_ITEM_SIZE_PER_PAGE
    }

    private fun updatePage() {
        loadCart(MAX_ITEM_SIZE_PER_PAGE)
        loadCount()
    }

    companion object {
        private const val MIN_PAGE = 0
        private const val MAX_ITEM_SIZE_PER_PAGE = 5
    }
}
