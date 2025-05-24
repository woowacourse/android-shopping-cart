package woowacourse.shopping.view.detail.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.Quantity
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.view.core.event.MutableSingleLiveData
import woowacourse.shopping.view.core.event.SingleLiveData
import woowacourse.shopping.view.detail.DetailUiEvent
import woowacourse.shopping.view.main.state.IncreaseState
import woowacourse.shopping.view.main.state.ProductState

class DetailViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
) : ViewModel() {
    private val _uiState = MutableLiveData<ProductState>()
    val uiState: LiveData<ProductState> get() = _uiState

    private val _event = MutableSingleLiveData<DetailUiEvent>()
    val event: SingleLiveData<DetailUiEvent> get() = _event

    fun load(productId: Long) {
        productRepository.getProduct(productId) {
            _uiState.postValue((ProductState(it, Quantity(1))))
        }
    }

    fun increaseCartQuantity() {
        withUiState { state ->
            when (val result = state.increaseCartQuantity()) {
                is IncreaseState.CanIncrease -> {
                    _uiState.value = result.value
                }

                is IncreaseState.CannotIncrease -> {
                    sendEvent(DetailUiEvent.ShowCannotIncrease(result.quantity))
                }
            }
        }
    }

    fun decreaseCartQuantity() {
        withUiState { state ->
            val decreasedCartQuantity = (state.cartQuantity - 1)

            val quantity =
                if (!decreasedCartQuantity.hasQuantity()) {
                    _event.setValue(DetailUiEvent.ShowCannotDecrease)
                    Quantity(1)
                } else {
                    decreasedCartQuantity
                }

            _uiState.value = state.copy(cartQuantity = quantity)
        }
    }

    fun saveCart() {
        withUiState { state ->
            cartRepository.getCart(state.item.id) { cart ->
                cart?.let {
                    cartRepository.upsert(state.item.id, state.cartQuantity)
                } ?: run {
                    cartRepository.modify(state.item.id, state.cartQuantity)
                }
            }
            sendEvent(DetailUiEvent.MoveToCart)
        }
    }

    private inline fun withUiState(block: (ProductState) -> Unit) {
        _uiState.value?.let(block)
    }

    private fun sendEvent(event: DetailUiEvent) {
        _event.setValue(event)
    }
}
