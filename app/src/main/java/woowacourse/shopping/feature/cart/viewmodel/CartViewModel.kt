package woowacourse.shopping.feature.cart.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.model.CartItem
import woowacourse.shopping.model.Product

class CartViewModel(private val cartRepository: CartRepository) : ViewModel() {
    private val _cart = MutableLiveData<List<CartItem>>()
    val cart: LiveData<List<CartItem>> get() = _cart

    private val _page = MutableLiveData<Int>(INITIALIZE_PAGE)
    val page: LiveData<Int> get() = _page

    private val _hasPage = MutableLiveData<Boolean>()
    val hasPage: LiveData<Boolean> get() = _hasPage

    private val _hasPreviousPage = MutableLiveData<Boolean>()
    val hasPreviousPage: LiveData<Boolean> get() = _hasPreviousPage

    private val _hasNextPage = MutableLiveData<Boolean>()
    val hasNextPage: LiveData<Boolean> get() = _hasNextPage

    private var cartSize: Int = INITIALIZE_CART_SIZE
    private var maxPage: Int = INITIALIZE_PAGE

    init {
        loadTotalCartCount()
    }

    fun deleteCartItem(product: Product) {
        if (isEmptyLastPage()) _page.value = _page.value?.minus(1)
        cartRepository.deleteCartItem(product)
        loadCart()
    }

    private fun isEmptyLastPage(): Boolean {
        return page() > 0 && cartSize % PAGE_SIZE == 1
    }

    fun loadCart() {
        _cart.value = cartRepository.findRange(page(), PAGE_SIZE)
    }

    private fun loadTotalCartCount() {
        cartSize = cartRepository.count()
        maxPage = (cartSize - 1) / PAGE_SIZE
        updatePageVisibility()
    }

    private fun updatePageVisibility() {
        _hasPage.value = changePageVisibility()
        _hasPreviousPage.value = changePreviousPageVisibility()
        _hasNextPage.value = changeNextPageVisibility()
    }

    fun moveNextPage() {
        _page.value = _page.value?.plus(1)
        loadCart()
    }

    fun movePreviousPage() {
        _page.value = _page.value?.minus(1)
        loadCart()
    }

    private fun changePageVisibility(): Boolean = cartSize > PAGE_SIZE

    private fun changePreviousPageVisibility(): Boolean = page() > INITIALIZE_PAGE

    private fun changeNextPageVisibility(): Boolean = page() < maxPage

    private fun page() = _page.value ?: INITIALIZE_PAGE

    companion object {
        private const val INITIALIZE_CART_SIZE = 0
        private const val INITIALIZE_PAGE = 0
        private const val PAGE_SIZE = 5
    }
}
