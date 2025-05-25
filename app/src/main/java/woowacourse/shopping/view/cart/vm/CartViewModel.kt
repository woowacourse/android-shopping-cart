package woowacourse.shopping.view.cart.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.view.cart.CartUiEvent
import woowacourse.shopping.view.cart.state.CartUiState
import woowacourse.shopping.view.cart.vm.Paging.Companion.INITIAL_PAGE_NO
import woowacourse.shopping.view.cart.vm.Paging.Companion.PAGE_SIZE
import woowacourse.shopping.view.core.common.withState
import woowacourse.shopping.view.core.event.MutableSingleLiveData
import woowacourse.shopping.view.core.event.SingleLiveData
import woowacourse.shopping.view.loader.CartLoader
import woowacourse.shopping.view.main.state.IncreaseState

class CartViewModel(
    private val cartRepository: CartRepository,
    private val cartLoader: CartLoader,
) : ViewModel() {
    private val paging = Paging(initialPage = INITIAL_PAGE_NO, pageSize = PAGE_SIZE)

    private val _uiState = MutableLiveData<CartUiState>()
    val uiState: LiveData<CartUiState> get() = _uiState

    private val _event = MutableSingleLiveData<CartUiEvent>()
    val event: SingleLiveData<CartUiEvent> get() = _event

    fun decreaseCartQuantity(productId: Long) {
        withState(_uiState.value) { state ->
            val result = state.decreaseCartQuantity(productId)

            _uiState.value = state.modifyUiState(result)
            cartRepository.upsert(productId, result.cartQuantity)
        }
    }

    fun increaseCartQuantity(productId: Long) {
        withState(_uiState.value) { state ->
            when (val result = state.canIncreaseCartQuantity(productId)) {
                is IncreaseState.CanIncrease -> {
                    val newState = result.value
                    _uiState.value = state.modifyUiState(newState)
                    cartRepository.upsert(productId, newState.cartQuantity)
                }

                is IncreaseState.CannotIncrease -> sendEvent(CartUiEvent.ShowCannotIncrease(result.quantity))
            }
        }
    }

    fun loadCarts() {
        val nextPage = paging.getPageNo() - 1
        cartLoader.invoke(
            nextPage,
            PAGE_SIZE,
        ) { carts, hasNextPage ->
            val pageState = paging.createPageState(hasNextPage)
            _uiState.postValue(CartUiState(items = carts, pageState = pageState))
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
        cartLoader.invoke(paging.getPageNo() - 1, PAGE_SIZE) { carts, hasNextPage ->
            if (paging.resetToLastPageIfEmpty(carts)) {
                refresh()
                return@invoke
            }

            val pageState = paging.createPageState(hasNextPage)
            _uiState.postValue(CartUiState(items = carts, pageState = pageState))
        }
    }

    private fun sendEvent(event: CartUiEvent) {
        _event.setValue(event)
    }
}
