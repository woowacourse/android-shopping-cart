package woowacourse.shopping.view.cart.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.Quantity
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.view.cart.CartUiEvent
import woowacourse.shopping.view.cart.state.CartUiState
import woowacourse.shopping.view.cart.vm.Paging.Companion.INITIAL_PAGE_NO
import woowacourse.shopping.view.cart.vm.Paging.Companion.PAGE_SIZE
import woowacourse.shopping.view.core.event.MutableSingleLiveData
import woowacourse.shopping.view.core.event.SingleLiveData
import woowacourse.shopping.view.main.state.CartSavingState
import woowacourse.shopping.view.main.state.IncreaseState
import woowacourse.shopping.view.main.state.ProductState

class CartViewModel(
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository,
) : ViewModel() {
    private val paging = Paging(initialPage = INITIAL_PAGE_NO, pageSize = PAGE_SIZE)

    private val _uiState = MutableLiveData<CartUiState>()
    val uiState: LiveData<CartUiState> get() = _uiState

    private val _event = MutableSingleLiveData<CartUiEvent>()
    val event: SingleLiveData<CartUiEvent> get() = _event

    fun decreaseCartQuantity(productId: Long) {
        val currentUiState = _uiState.value ?: return
        val decreasedCartQuantity = currentUiState.decreaseCartQuantity(productId)

        _uiState.value = currentUiState.modifyUiState(decreasedCartQuantity)
    }

    fun increaseCartQuantity(productId: Long) {
        val currentUiState = _uiState.value ?: return

        when (currentUiState.isAddedProduct(productId)) {
            CartSavingState.SAVED -> {
                whenProductSavedInCart(productId, currentUiState)
            }

            CartSavingState.NOT_SAVED -> {
                whenProductNotSavedInCart(productId, currentUiState)
            }
        }
    }

    private fun whenProductSavedInCart(
        productId: Long,
        currentUiState: CartUiState,
    ) {
        handleIncreaseCartQuantity(productId, currentUiState) { quantity ->
            cartRepository.modifyQuantity(productId, quantity)
        }
    }

    private fun whenProductNotSavedInCart(
        productId: Long,
        currentUiState: CartUiState,
    ) {
        handleIncreaseCartQuantity(productId, currentUiState) {
            cartRepository.insert(productId)
        }
    }

    private fun handleIncreaseCartQuantity(
        productId: Long,
        currentUiState: CartUiState,
        onCartUpdate: (Quantity) -> Unit,
    ) {
        when (val result = currentUiState.canIncreaseCartQuantity(productId)) {
            is IncreaseState.CanIncrease -> {
                val newState = result.value
                _uiState.value = currentUiState.modifyUiState(newState)
                onCartUpdate(newState.cartQuantity)
            }

            is IncreaseState.CannotIncrease -> sendEvent(CartUiEvent.ShowCannotIncrease(result.quantity))
        }
    }

    fun loadCarts() {
        val result = cartRepository.loadSinglePage(paging.getPageNo() - 1, PAGE_SIZE)

        val carts =
            result
                .carts
                .map { cart ->
                    val product = productRepository[cart.productId]
                    ProductState(product, cart.quantity)
                }
        val pageState = paging.createPageState(result.hasNextPage)

        _uiState.value = CartUiState(items = carts, pageState = pageState)
    }

    fun deleteProduct(cardId: Long) {
        cartRepository.delete(cardId)
        loadCarts()

        val currentCarts = _uiState.value?.items
        if (paging.resetToLastPageIfEmpty(currentCarts)) {
            loadCarts()
        }
    }

    fun addPage() {
        paging.moveToNextPage()
        loadCarts()
    }

    fun subPage() {
        paging.moveToPreviousPage()
        loadCarts()
    }

    private fun sendEvent(event: CartUiEvent) {
        _event.setValue(event)
    }
}
