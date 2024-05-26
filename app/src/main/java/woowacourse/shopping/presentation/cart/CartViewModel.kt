package woowacourse.shopping.presentation.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.presentation.base.BaseViewModelFactory
import woowacourse.shopping.presentation.util.MutableSingleLiveData
import woowacourse.shopping.presentation.util.SingleLiveData

class CartViewModel(
    private val cartRepository: CartRepository,
) : ViewModel() {
    private val _errorEvent = MutableSingleLiveData<CartErrorEvent>()
    val errorEvent: SingleLiveData<CartErrorEvent> = _errorEvent

    private val _uiState = MutableLiveData<CartUiState>()
    val uiState: LiveData<CartUiState> get() = _uiState

    init {
        loadCartProducts(START_PAGE)
    }

    fun increaseCartProduct(productId: Long) {
        val product = _uiState.value?.findProduct(productId) ?: return
        cartRepository.updateCartProduct(productId, product.count).onSuccess {
            val newUiState =
                _uiState.value?.increaseProductCount(productId, INCREMENT_AMOUNT) ?: return
            updateUiState(newUiState)
        }.onFailure {
            _errorEvent.setValue(CartErrorEvent.UpdateCartProducts)
        }
    }

    fun decreaseCartProduct(productId: Long) {
        val product = _uiState.value?.findProduct(productId) ?: return
        val uiState = _uiState.value ?: return
        if (!uiState.canDecreaseProductCount(productId, CART_PRODUCT_COUNT_LIMIT)) {
            return _errorEvent.setValue(CartErrorEvent.DecreaseCartCountLimit)
        }
        cartRepository.updateCartProduct(productId, product.count).onSuccess {
            val newUiState = uiState.decreaseProductCount(productId, INCREMENT_AMOUNT)
            updateUiState(newUiState)
        }.onFailure {
            _errorEvent.setValue(CartErrorEvent.UpdateCartProducts)
        }
    }

    fun deleteProduct(product: CartProductUi) {
        cartRepository.deleteCartProduct(product.product.id).onSuccess {
            refreshCartProducts()
        }.onFailure {
            _errorEvent.setValue(CartErrorEvent.DeleteCartProduct)
        }
    }

    fun moveToNextPage() {
        val currentPage = uiState.value?.currentPage ?: return
        loadCartProducts(currentPage + INCREMENT_AMOUNT)
    }

    fun moveToPrevPage() {
        val currentPage = uiState.value?.currentPage ?: return
        loadCartProducts(currentPage - INCREMENT_AMOUNT)
    }

    private fun updateUiState(
        products: List<CartProductUi> = _uiState.value?.products ?: emptyList(),
        currentPage: Int = _uiState.value?.currentPage ?: START_PAGE
    ) {
        val canLoadPrevPage = canLoadMoreCartProducts(currentPage - INCREMENT_AMOUNT)
        val canLoadNextPage = canLoadMoreCartProducts(currentPage + INCREMENT_AMOUNT)

        _uiState.value = CartUiState(
            products = products,
            currentPage = currentPage,
            canLoadPrevPage = canLoadPrevPage,
            canLoadNextPage = canLoadNextPage
        )
    }

    private fun updateUiState(
        newUiState: CartUiState,
    ) {
        val currentPage = newUiState.currentPage
        val canLoadPrevPage = canLoadMoreCartProducts(currentPage - INCREMENT_AMOUNT)
        val canLoadNextPage = canLoadMoreCartProducts(currentPage + INCREMENT_AMOUNT)
        _uiState.value = newUiState.copy(
            canLoadPrevPage = canLoadPrevPage,
            canLoadNextPage = canLoadNextPage
        )
    }

    private fun loadCartProducts(page: Int) {
        cartRepository.cartProducts(page, PAGE_SIZE).onSuccess { carts ->
            val newProducts = carts.map { it.toUiModel() }
            updateUiState(products = newProducts, currentPage = page)
        }.onFailure {
            _errorEvent.setValue(CartErrorEvent.LoadCartProducts)
        }
    }

    private fun refreshCartProducts() {
        val currentPage = _uiState.value?.currentPage ?: START_PAGE
        loadCartProducts(currentPage)
    }

    private fun canLoadMoreCartProducts(page: Int): Boolean {
        cartRepository.canLoadMoreCartProducts(page, PAGE_SIZE).onSuccess {
            return it
        }.onFailure {
            _errorEvent.setValue(CartErrorEvent.CanLoadMoreCartProducts)
        }
        return false
    }

    companion object {
        private const val START_PAGE: Int = 1
        private const val PAGE_SIZE = 5
        private const val INCREMENT_AMOUNT = 1
        private const val CART_PRODUCT_COUNT_LIMIT = 1

        fun factory(repository: CartRepository): ViewModelProvider.Factory {
            return BaseViewModelFactory { CartViewModel(repository) }
        }
    }
}