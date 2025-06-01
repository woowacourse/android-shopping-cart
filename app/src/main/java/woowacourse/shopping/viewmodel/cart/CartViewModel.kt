package woowacourse.shopping.viewmodel.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import woowacourse.shopping.model.products.CartState
import woowacourse.shopping.model.products.Product
import woowacourse.shopping.model.products.ShoppingCartItem
import woowacourse.shopping.repository.ShoppingCartRepository
import woowacourse.shopping.repository.ShoppingCartRepositoryImpl

class CartViewModel(
    private val shoppingCartRepository: ShoppingCartRepository,
) : ViewModel() {
    private val _cartState = MutableLiveData<CartState>()
    val cartState: LiveData<CartState> get() = _cartState

    private val _currentPageItems = MutableLiveData<List<ShoppingCartItem>>()
    val currentPageItems: LiveData<List<ShoppingCartItem>> get() = _currentPageItems

    private val _pageCount = MutableLiveData(1)
    val pageCount: LiveData<Int> get() = _pageCount

    private val pageSize = 5

    init {
        refreshCartState()
    }

    fun addToCart(product: Product) {
        val newState = shoppingCartRepository.addProduct(product)
        _cartState.value = newState
    }

    fun updateQuantity(
        productId: Int,
        quantity: Int,
    ) {
        val newState = shoppingCartRepository.updateQuantity(productId, quantity)
        _cartState.value = newState
        val currentPage = _pageCount.value ?: 1
        loadPage(currentPage)
    }

    fun removeFromCart(productId: Int) {
        val newState = shoppingCartRepository.removeProduct(productId)
        _cartState.value = newState
        val currentPage = _pageCount.value ?: 1
        loadPage(currentPage)
    }

    fun getQuantity(productId: Int): Int = _cartState.value?.getQuantity(productId) ?: 0

    fun loadPage(page: Int) {
        val cartState = _cartState.value ?: CartState()
        val allItems = cartState.getAllShoppingCartItem()

        if (allItems.isEmpty()) {
            _currentPageItems.value = emptyList()
            _pageCount.value = 1
            return
        }

        val totalPages = (allItems.size + pageSize - 1) / pageSize
        val validPage = page.coerceIn(1, totalPages)

        val start = (validPage - 1) * pageSize
        val end = minOf(start + pageSize, allItems.size)

        val pageItems = allItems.subList(start, end)
        _currentPageItems.value = pageItems
        _pageCount.value = validPage
    }

    fun loadNextPage() {
        val cartState = _cartState.value ?: CartState()
        val allItems = cartState.getAllShoppingCartItem()
        val totalPages = (allItems.size + pageSize - 1) / pageSize
        val currentPage = _pageCount.value ?: 1

        if (currentPage < totalPages) {
            loadPage(currentPage + 1)
        }
    }

    fun loadPreviousPage() {
        val currentPage = _pageCount.value ?: 1
        if (currentPage > 1) {
            loadPage(currentPage - 1)
        }
    }

    fun isFirstPage(): Boolean = (_pageCount.value ?: 1) == 1

    fun isLastPage(): Boolean {
        val cartState = _cartState.value ?: CartState()
        val allItems = cartState.getAllShoppingCartItem()
        val totalPages = if (allItems.isEmpty()) 1 else (allItems.size + pageSize - 1) / pageSize
        return (_pageCount.value ?: 1) == totalPages
    }

    fun isOnlyOnePage(): Boolean {
        val cartState = _cartState.value ?: CartState()
        return cartState.getAllShoppingCartItem().size <= pageSize
    }

    fun refreshCartState() {
        _cartState.value = shoppingCartRepository.getCurrentState()
        loadPage(1)
    }

    companion object {
        val Factory: ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras,
                ): T = CartViewModel(ShoppingCartRepositoryImpl.getInstance()) as T
            }
    }
}
