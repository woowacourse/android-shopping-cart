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
        withUiState { state ->
            val result = state.decreaseCartQuantity(productId)
            handleDecreaseQuantity(state, result, productId)
        }
    }

    fun increaseCartQuantity(productId: Long) {
        withUiState { state ->
            val result = state.canIncreaseCartQuantity(productId)
            handleIncreaseQuantity(state, result, productId)
        }
    }

    fun syncCartQuantities() {
        withUiState { state ->
            val products = state.items
            val productIds = state.productIds

            cartRepository.getCarts(productIds) { carts ->
                val synced =
                    products.mapIndexed { index, state ->
                        val quantity = carts[index]?.quantity ?: Quantity(0)
                        state.copy(cartQuantity = quantity)
                    }
                _uiState.postValue(state.copy(items = synced))
            }
        }
    }

    private fun handleIncreaseQuantity(
        uiState: ProductUiState,
        state: IncreaseState,
        productId: Long,
    ) {
        when (state) {
            is IncreaseState.CanIncrease -> {
                val newState = state.value
                _uiState.value = uiState.modifyUiState(newState)
                cartRepository.upsert(productId, state.value.cartQuantity)
            }

            is IncreaseState.CannotIncrease -> sendEvent(MainUiEvent.ShowCannotIncrease(state.quantity))
        }
    }

    private fun handleDecreaseQuantity(
        uiState: ProductUiState,
        decreaseResult: ProductState,
        productId: Long,
    ) {
        _uiState.value = uiState.modifyUiState(decreaseResult)

        if (!decreaseResult.cartQuantity.hasQuantity()) {
            cartRepository.delete(productId)
        } else {
            cartRepository.upsert(productId, decreaseResult.cartQuantity)
        }
    }

    private fun sendEvent(event: MainUiEvent) {
        _uiEvent.setValue(event)
    }

    private inline fun withUiState(block: (ProductUiState) -> Unit) {
        _uiState.value?.let(block)
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}
