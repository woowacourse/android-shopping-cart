package woowacourse.shopping.view.cart.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.view.cart.CartUiEvent
import woowacourse.shopping.view.cart.state.CartUiState
import woowacourse.shopping.view.cart.vm.Paging.Companion.INITIAL_PAGE_NO
import woowacourse.shopping.view.cart.vm.Paging.Companion.PAGE_SIZE
import woowacourse.shopping.view.core.event.MutableSingleLiveData
import woowacourse.shopping.view.core.event.SingleLiveData
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
        val result = currentUiState.decreaseCartQuantity(productId)

        _uiState.value = currentUiState.modifyUiState(result)
        cartRepository.upsert(productId, result.cartQuantity)
    }

    fun increaseCartQuantity(productId: Long) {
        val currentUiState = _uiState.value ?: return

        when (val result = currentUiState.canIncreaseCartQuantity(productId)) {
            is IncreaseState.CanIncrease -> {
                val newState = result.value
                _uiState.value = currentUiState.modifyUiState(newState)
                cartRepository.upsert(productId, newState.cartQuantity)
            }

            is IncreaseState.CannotIncrease -> sendEvent(CartUiEvent.ShowCannotIncrease(result.quantity))
        }
    }

    fun loadCarts() {
        val nextPage = paging.getPageNo() - 1
        cartRepository.singlePage(nextPage, PAGE_SIZE) { page ->
            val products =
                page.carts.map { cart ->
                    val product = productRepository[cart.productId]
                    ProductState(product, cart.quantity)
                }
            val pageState = paging.createPageState(page.hasNextPage)
            _uiState.postValue(CartUiState(items = products, pageState = pageState))
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

    fun deleteProduct(productId: Long) {
        cartRepository.delete(productId) {
            refresh()
        }
    }

    private fun refresh() {
        cartRepository.singlePage(paging.getPageNo() - 1, PAGE_SIZE) { page ->
            val products =
                page.carts.map { cart ->
                    val product = productRepository[cart.productId]
                    ProductState(product, cart.quantity)
                }

            if (paging.resetToLastPageIfEmpty(products)) {
                refresh()
                return@singlePage
            }

            val pageState = paging.createPageState(page.hasNextPage)
            _uiState.postValue(CartUiState(items = products, pageState = pageState))
        }
    }

    private fun sendEvent(event: CartUiEvent) {
        _event.setValue(event)
    }
}
