package woowacourse.shopping.view.main.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.Quantity
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.HistoryRepository
import woowacourse.shopping.view.core.event.MutableSingleLiveData
import woowacourse.shopping.view.core.event.SingleLiveData
import woowacourse.shopping.view.loader.HistoryLoader
import woowacourse.shopping.view.loader.ProductLoader
import woowacourse.shopping.view.main.MainUiEvent
import woowacourse.shopping.view.main.state.IncreaseState
import woowacourse.shopping.view.main.state.LoadState
import woowacourse.shopping.view.main.state.ProductState
import woowacourse.shopping.view.main.state.ProductUiState

class MainViewModel(
    private val cartRepository: CartRepository,
    private val historyRepository: HistoryRepository,
    private val productLoader: ProductLoader,
    private val historyLoader: HistoryLoader,
) : ViewModel() {
    private val _uiState = MutableLiveData(ProductUiState())
    val uiState: LiveData<ProductUiState> get() = _uiState

    private val _uiEvent = MutableSingleLiveData<MainUiEvent>()
    val uiEvent: SingleLiveData<MainUiEvent> get() = _uiEvent

    init {
        productLoader(INITIAL_PAGE, PAGE_SIZE) { productStates, hasNextPage ->
            historyLoader { historyStates ->
                _uiState.postValue(
                    ProductUiState(
                        productItems = productStates,
                        historyItems = historyStates,
                        load = LoadState.of(hasNextPage),
                    ),
                )
            }
        }
    }

    fun loadPage() {
        withUiState { state ->
            val newPage = state.productItemCount().div(PAGE_SIZE)

            productLoader.invoke(
                pageIndex = newPage,
                pageSize = PAGE_SIZE,
            ) { productStates, hasNext ->
                val newState = state.addItems(productStates, hasNext)
                _uiState.postValue(newState)
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
            val products = state.productItems
            val productIds = state.productIds

            cartRepository.getCarts(productIds) { carts ->
                val synced =
                    products.mapIndexed { index, productState ->
                        val quantity = carts.getOrNull(index)?.quantity ?: Quantity(0)
                        productState.copy(cartQuantity = quantity)
                    }
                _uiState.postValue(state.copy(productItems = synced))
            }
        }
    }

    fun saveHistory(productId: Long) {
        historyRepository.saveHistory(productId) {
            _uiEvent.postValue(MainUiEvent.NavigateToDetail(productId))
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
                cartRepository.upsert(productId, newState.cartQuantity)
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
        private const val INITIAL_PAGE = 0
        private const val PAGE_SIZE = 20
    }
}
