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
        val newProduct = _uiState.value?.increaseProductCount(productId, INCREMENT_AMOUNT) ?: return
        val currentProducts = _uiState.value?.products ?: return
        updateProductCounts(productId, newProduct, currentProducts)
    }

    fun decreaseCartProduct(productId: Long) {
        val newProduct = _uiState.value?.decreaseProductCount(productId, INCREMENT_AMOUNT) ?: return
        val currentProducts = _uiState.value?.products ?: return
        updateProductCounts(productId, newProduct, currentProducts)
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

    private fun updateProductCounts(
        productId: Long,
        newProduct: CartProductUi,
        currentProducts: List<CartProductUi>
    ) {
        if (newProduct.count <= CART_PRODUCT_COUNT_LIMIT) return _errorEvent.setValue(CartErrorEvent.DecreaseCartCountLimit)
        cartRepository.updateCartProduct(productId, newProduct.count).onSuccess {
            val updatedProducts =
                currentProducts.updateIf(predicate = { it.product.id == productId }) { newProduct }
            updateUiState(products = updatedProducts)
        }.onFailure {
            _errorEvent.setValue(CartErrorEvent.UpdateCartProducts)
        }
    }

    private inline fun <T> Iterable<T>.updateIf(
        predicate: (T) -> Boolean,
        transform: (T) -> T,
    ): List<T> {
        return map { if (predicate(it)) transform(it) else it }
    }

    companion object {
        private const val START_PAGE: Int = 1
        private const val PAGE_SIZE = 5
        private const val INCREMENT_AMOUNT = 1
        private const val CART_PRODUCT_COUNT_LIMIT = 0

        fun factory(repository: CartRepository): ViewModelProvider.Factory {
            return BaseViewModelFactory { CartViewModel(repository) }
        }
    }
}

data class CartUiState(
    val products: List<CartProductUi>,
    val currentPage: Int,
    val canLoadPrevPage: Boolean,
    val canLoadNextPage: Boolean
) {
    fun increaseProductCount(productId: Long, amount: Int): CartProductUi? =
        products.find { it.product.id == productId }
            ?.let { it.copy(count = it.count + amount) }

    fun decreaseProductCount(productId: Long, amount: Int): CartProductUi? =
        products.find { it.product.id == productId }
            ?.let { it.copy(count = it.count - amount) }


    fun deleteProduct(productId: Long): List<CartProductUi> =
        products.filter { it.product.id != productId }
}