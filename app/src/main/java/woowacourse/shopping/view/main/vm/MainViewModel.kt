package woowacourse.shopping.view.main.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.Quantity
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.view.core.event.MutableSingleLiveData
import woowacourse.shopping.view.core.event.SingleLiveData
import woowacourse.shopping.view.main.vm.event.MainUiEvent
import woowacourse.shopping.view.main.vm.state.CartSavingState
import woowacourse.shopping.view.main.vm.state.IncreaseState
import woowacourse.shopping.view.main.vm.state.ProductState
import woowacourse.shopping.view.main.vm.state.ProductUiState

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

        val productsResult = productRepository.loadSinglePage(newPage, PAGE_SIZE)

        val result =
            productsResult
                .products
                .map { product ->
                    val cart = cartRepository[product.id]
                    val quantity = cart.quantity

                    ProductState(product, quantity)
                }

        _uiState.value = _uiState.value?.addItems(result, productsResult.hasNextPage)
    }

    fun decreaseCartQuantity(productId: Long) {
        val currentUiState = _uiState.value ?: return
        val (result, quantity) = currentUiState.decreaseCartQuantity(productId)

        _uiState.value = currentUiState.modifyUiState(result)
        productRepository.modifyQuantity(productId, quantity)
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
        currentUiState: ProductUiState,
    ) {
        handleIncreaseCartQuantity(productId, currentUiState) { quantity ->
            cartRepository.modifyQuantity(productId, quantity)
        }
    }

    private fun whenProductNotSavedInCart(
        productId: Long,
        currentUiState: ProductUiState,
    ) {
        handleIncreaseCartQuantity(productId, currentUiState) {
            cartRepository.insert(productId)
        }
    }

    private fun handleIncreaseCartQuantity(
        productId: Long,
        currentUiState: ProductUiState,
        onCartUpdate: (Quantity) -> Unit,
    ) {
        when (val result = currentUiState.canIncreaseCartQuantity(productId)) {
            is IncreaseState.CanIncrease -> {
                val newState = result.value
                _uiState.value = currentUiState.modifyUiState(newState)
                productRepository.modifyQuantity(productId, result.productStock)
                onCartUpdate(newState.cartQuantity)
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
