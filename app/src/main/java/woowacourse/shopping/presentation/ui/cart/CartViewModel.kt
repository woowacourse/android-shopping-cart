package woowacourse.shopping.presentation.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.ProductListItem
import woowacourse.shopping.domain.ProductListItem.ShoppingProductItem.Companion.joinProductAndCart
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.presentation.ui.UiState
import woowacourse.shopping.presentation.util.Event

class CartViewModel(private val cartRepository: CartRepository) : ViewModel() {
    private val _isRightButtonEnabled = MutableLiveData<Boolean>()
    val isRightButtonEnabled: LiveData<Boolean> get() = _isRightButtonEnabled

    private val _isLeftButtonEnabled = MutableLiveData<Boolean>()
    val isLeftButtonEnabled: LiveData<Boolean> get() = _isLeftButtonEnabled

    private val _pageControllerIsVisible = MutableLiveData<Boolean>()
    val pageControllerIsVisible: LiveData<Boolean> get() = _pageControllerIsVisible

    private var maxPage: Int = 0

    private val _currentPage = MutableLiveData(0)
    val currentPage: LiveData<Int> get() = _currentPage

    private val _error = MutableLiveData<Event<CartError>>()

    val error: LiveData<Event<CartError>> = _error

    private val _shoppingProducts =
        MutableLiveData<UiState<List<ProductListItem.ShoppingProductItem>>>(UiState.None)

    val shoppingProducts: LiveData<UiState<List<ProductListItem.ShoppingProductItem>>> get() = _shoppingProducts

    private val cartProducts = mutableListOf<Cart>()

    private val _changedCartProducts = MutableLiveData<List<Cart>>()
    val changedCartProducts: LiveData<List<Cart>> get() = _changedCartProducts

    init {
        fetchMaxPage()
    }

    private fun fetchMaxPage() {
        cartRepository.getMaxPage(PAGE_SIZE).onSuccess { maxPage = it }
    }

    fun deleteProduct(product: Product) {
        cartRepository.deleteProduct(product).onSuccess {
            val originalChangedCartProducts = changedCartProducts.value ?: emptyList()
            _changedCartProducts.value = originalChangedCartProducts.plus(Cart(product, 0))
        }.onFailure {
            _error.value = Event(CartError.CartItemNotDeleted)
        }
        fetchMaxPage()
        updatePageController()
        loadProductByPage(currentPage.value ?: 0)
    }

    fun updatePageController() {
        if (currentPage.value != null) {
            _currentPage.value = currentPage.value!!.coerceIn(0, maxPage)
            _isLeftButtonEnabled.value = currentPage.value!! > 0
            _isRightButtonEnabled.value = currentPage.value!! < maxPage
        }
        _pageControllerIsVisible.value = maxPage > 0
    }

    fun loadProductByPage(newPage: Int) {
        cartRepository.load(newPage, PAGE_SIZE).onSuccess {
            cartProducts.apply {
                clear()
                addAll(it)
            }
            _shoppingProducts.value =
                UiState.Success(
                    cartProducts.map {
                        joinProductAndCart(
                            it.product,
                            it,
                        )
                    },
                )
        }.onFailure {
            _error.value = Event(CartError.CartItemsNotFound)
        }
    }

    fun turnNextPage() {
        if (currentPage.value == maxPage) return
        val newPage = ((currentPage.value ?: 0) + 1).coerceAtMost(maxPage)
        _currentPage.value = newPage
        loadProductByPage(newPage)
    }

    fun turnPreviousPage() {
        if (currentPage.value == 0) return
        val newPage = (currentPage.value ?: 0) - 1
        _currentPage.value = newPage
        loadProductByPage(newPage)
    }

    fun updateCartItemQuantity(
        product: Product,
        quantityDelta: Int,
    ) {
        cartRepository.modifyQuantity(product, quantityDelta).onSuccess {
            modifyShoppingProductQuantity(product.id, quantityDelta)
        }
    }

    private fun modifyShoppingProductQuantity(
        productId: Long,
        quantityDelta: Int,
    ) {
        val productIndex = cartProducts.indexOfFirst { it.product.id == productId }
        val originalQuantity = cartProducts[productIndex].quantity
        val updatedItem =
            cartProducts[productIndex].copy(
                quantity =
                    (originalQuantity + quantityDelta).coerceAtLeast(
                        0,
                    ),
            )
        cartProducts[productIndex] = updatedItem
        _shoppingProducts.value =
            UiState.Success(
                cartProducts.map {
                    joinProductAndCart(
                        it.product,
                        it,
                    )
                },
            )
        val originalChangedCartProducts = changedCartProducts.value ?: emptyList()
        _changedCartProducts.value = originalChangedCartProducts.plus(updatedItem)
    }

    companion object {
        private const val PAGE_SIZE = 5
    }
}
