package woowacourse.shopping.view.main.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.Quantity
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.view.core.event.MutableSingleLiveData
import woowacourse.shopping.view.core.event.SingleLiveData
import woowacourse.shopping.view.main.MainUiEvent
import woowacourse.shopping.view.main.state.IncreaseState
import woowacourse.shopping.view.main.state.ProductState
import woowacourse.shopping.view.main.state.ProductUiState

class MainViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
) : ViewModel() {
    private val _uiState = MutableLiveData(ProductUiState())
    val uiState: LiveData<ProductUiState> get() = _uiState

    private val _uiEvent = MutableSingleLiveData<MainUiEvent>()
    val uiEvent: SingleLiveData<MainUiEvent> get() = _uiEvent

    init {
        loadProducts()
    }

    fun loadProducts() {
        val newPage = _uiState.value?.itemCount()?.div(PAGE_SIZE) ?: 0

        productRepository.loadSinglePage(newPage, PAGE_SIZE) { page ->
            val ids = page.products.map { it.id }
            val products = page.products

            cartRepository.getCarts(ids) { carts ->
                val result =
                    products.mapIndexed { idx, product ->
                        val quantity = carts[idx]?.quantity ?: Quantity(0)
                        ProductState(product, quantity)
                    }

                _uiState.postValue(
                    _uiState.value?.addItems(result, page.hasNextPage),
                )
            }
        }
    }

    fun decreaseCartQuantity(productId: Long) {
        val currentUiState = _uiState.value ?: return
        val result = currentUiState.decreaseCartQuantity(productId)

        _uiState.value = currentUiState.modifyUiState(result)

        if (!result.cartQuantity.hasQuantity()) {
            cartRepository.delete(productId) {
            }
        } else {
            cartRepository.upsert(productId, result.cartQuantity)
        }
    }

    fun increaseCartQuantity(productId: Long) {
        val currentUiState = _uiState.value ?: return

        when (val result = currentUiState.canIncreaseCartQuantity(productId)) {
            is IncreaseState.CanIncrease -> {
                val newState = result.value
                _uiState.value = currentUiState.modifyUiState(newState)
                cartRepository.upsert(productId, result.value.cartQuantity)
            }

            is IncreaseState.CannotIncrease -> sendEvent(MainUiEvent.ShowCannotIncrease(result.quantity))
        }
    }

    private fun sendEvent(event: MainUiEvent) {
        _uiEvent.setValue(event)
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}
