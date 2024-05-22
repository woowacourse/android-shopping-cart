package woowacourse.shopping.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.model.CartItem
import woowacourse.shopping.model.Product

class CartViewModel(private val cartRepository: CartRepository) : ViewModel() {
    private val _cart = MutableLiveData<MutableList<CartItem>>()
    val cart: LiveData<List<CartItem>> = _cart.map { it.toList() }

    private val _page = MutableLiveData<Int>(INITIALIZE_PAGE)
    val page: LiveData<Int> get() = _page

    private val totalCartCount = MutableLiveData<Int>(INITIALIZE_CART_SIZE)

    val hasPage: LiveData<Boolean> = totalCartCount.map { changePageVisibility(it) }
    val hasPreviousPage: LiveData<Boolean> = _page.map { changePreviousPageVisibility(it) }
    val hasNextPage: LiveData<Boolean> = _page.map { changeNextPageVisibility(it) }
    val isEmptyCart: LiveData<Boolean> = _cart.map { it.isEmpty() }

    private var maxPage: Int = INITIALIZE_PAGE

    private val _changedCartItemQuantity = MutableLiveData<CartItem>()
    val changedCartItemQuantity: LiveData<CartItem> get() = _changedCartItemQuantity

    private val _removedCartItemId = MutableLiveData<Long>()
    val removedCartItemId: LiveData<Long> get() = _removedCartItemId

    init {
        loadCart()
    }

    private fun loadCart() {
        val page = _page.value ?: INITIALIZE_PAGE
        _cart.value = cartRepository.findRange(page, PAGE_SIZE).toMutableList()
        loadTotalCartCount()
    }

    private fun loadTotalCartCount() {
        val totalCartCount = cartRepository.totalProductCount()
        this.totalCartCount.value = totalCartCount
        maxPage = (totalCartCount - 1) / PAGE_SIZE
    }

    fun deleteCartItem(cartItem: CartItem) {
        cartRepository.deleteCartItem(cartItem)
        if (isEmptyLastPage()) {
            movePreviousPage()
            return
        }
        updateDeletedCart(cartItem)
    }

    private fun updateDeletedCart(cartItem: CartItem) {
        _cart.value = _cart.value?.apply { remove(cartItem) }
        totalCartCount.value = totalCartCount.value?.minus(1)
    }

    private fun isEmptyLastPage(): Boolean {
        val page = _page.value ?: INITIALIZE_PAGE
        val totalCartCount = totalCartCount.value ?: INITIALIZE_CART_SIZE
        return page > 0 && totalCartCount % PAGE_SIZE == 1
    }

    private fun changePageVisibility(totalCartCount: Int): Boolean = totalCartCount > PAGE_SIZE

    private fun changePreviousPageVisibility(page: Int): Boolean = page > INITIALIZE_PAGE

    private fun changeNextPageVisibility(page: Int): Boolean = page < maxPage

    fun moveNextPage() {
        _page.value = _page.value?.plus(1)
        loadCart()
    }

    fun movePreviousPage() {
        _page.value = _page.value?.minus(1)
        loadCart()
    }

    fun increaseQuantity(product: Product) {
        cartRepository.increaseQuantity(product)
        _changedCartItemQuantity.value = cartRepository.find(product)
    }

    fun decreaseQuantity(product: Product) {
        val decreaseQuantityCartItem = cartRepository.find(product)
        cartRepository.decreaseQuantity(product)
        runCatching {
            cartRepository.find(product)
        }.onSuccess {
            _changedCartItemQuantity.value = it
        }.onFailure {
            _removedCartItemId.value = decreaseQuantityCartItem.id
        }
    }

    companion object {
        private const val INITIALIZE_CART_SIZE = 0
        private const val INITIALIZE_PAGE = 0
        private const val PAGE_SIZE = 5
    }
}
