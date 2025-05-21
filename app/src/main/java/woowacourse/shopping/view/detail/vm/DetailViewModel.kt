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
import woowacourse.shopping.view.main.vm.state.IncreaseState
import woowacourse.shopping.view.main.vm.state.ProductState

class DetailViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
) : ViewModel() {
    private val _uiState = MutableLiveData<ProductState>()
    val uiState: LiveData<ProductState> get() = _uiState

    private val _event = MutableSingleLiveData<DetailUiEvent>()
    val event: SingleLiveData<DetailUiEvent> get() = _event

    fun load(productId: Long) {
        val product = productRepository[productId]
        _uiState.value = ProductState(product, Quantity(1))
    }

    fun increaseCartQuantity() {
        val currentUiState = _uiState.value ?: return

        when (val result = currentUiState.increaseCartQuantity()) {
            is IncreaseState.CanIncrease -> {
                _uiState.value = result.value
            }

            is IncreaseState.CannotIncrease -> {
                sendEvent(DetailUiEvent.ShowCannotIncrease(result.quantity))
            }
        }
    }

    fun decreaseCartQuantity() {
        val currentUiState = _uiState.value ?: return
        val decreasedCartQuantity = (currentUiState.cartQuantity - 1)

        val quantity =
            if (!decreasedCartQuantity.hasQuantity()) {
                _event.setValue(DetailUiEvent.ShowCannotDecrease)
                Quantity(1)
            } else {
                decreasedCartQuantity
            }

        _uiState.value = currentUiState.copy(cartQuantity = quantity)
    }

    fun saveCart() {
        val state = _uiState.value ?: return
        cartRepository[state.item.id]?.let {
            cartRepository.modifyQuantity(state.item.id, state.cartQuantity)
        } ?: run {
            cartRepository.insert(state.item.id, state.cartQuantity.value)
        }
        sendEvent(DetailUiEvent.MoveToCart)
    }

    private fun sendEvent(event: DetailUiEvent) {
        _event.setValue(event)
    }
}
