package woowacourse.shopping.ui.productdetail.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.Quantity
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.RecentProductRepository
import woowacourse.shopping.ui.model.DetailProductUiModel
import woowacourse.shopping.ui.model.LatestProductUiModel
import woowacourse.shopping.ui.productdetail.state.ProductDetailUiState
import woowacourse.shopping.ui.productdetail.state.UiEvent

class ProductDetailViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val recentProductRepository: RecentProductRepository,
) : ViewModel() {
    private val productId: String = savedStateHandle["product_id"] ?: ""

    private val _uiState = MutableStateFlow(ProductDetailUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var currentProduct: Product? = null

    init {
        loadProduct()
    }

    private fun loadProduct() {
        viewModelScope.launch {
            productRepository.getProduct(productId)
                .onSuccess { product ->
                    if (product == null) {
                        _uiState.update { it.copy(isError = true) }
                        return@launch
                    }

                    handleProductSuccess(product)
                }
                .onFailure { exception ->
                    _uiState.update { it.copy(isError = true, errorMessage = exception.message) }
                }
        }
    }

    private suspend fun handleProductSuccess(product: Product) {
        currentProduct = product

        val recentProducts = recentProductRepository.getRecentProducts().firstOrNull() ?: emptyList()
        val latestProduct = recentProducts.firstOrNull()

        val latestProductUiModel = latestProduct
            ?.takeIf { it.id != productId }
            ?.let { toLatestProductUiModel(it) }

        recentProductRepository.addRecentProduct(product)

        _uiState.update { state ->
            state.copy(
                isError = false,
                product = toDetailProductUiModel(product, Quantity(state.selectedQuantity)),
                latestProduct = latestProductUiModel,
            )
        }
    }

    fun addCartItem() {
        val product = currentProduct ?: return
        val quantity = Quantity(_uiState.value.selectedQuantity)

        viewModelScope.launch {
            try {
                val currentCartItems = cartRepository.getCart().first()

                val updatedCart = Cart(currentCartItems).plusProduct(product, quantity)
                val updatedItem = updatedCart.findCartItemById(product.id) ?: return@launch
                cartRepository.updateCartItem(updatedItem)
                _uiEvent.send(UiEvent.ShowToast("장바구니에 담았습니다!"))
                _uiEvent.send(UiEvent.CartAddSuccess)
            } catch (e: CancellationException) {
                throw e
            } catch (_: Exception) {
                _uiEvent.send(UiEvent.ShowToast("장바구니 담기에 실패했습니다."))
            }
        }
    }

    fun increment() {
        val product = currentProduct ?: return
        _uiState.update { state ->
            val count = state.selectedQuantity + 1
            state.copy(
                selectedQuantity = count,
                product = toDetailProductUiModel(product, Quantity(count)),
            )
        }
    }

    fun decrement() {
        val product = currentProduct ?: return
        _uiState.update { state ->
            if (state.selectedQuantity == 1) {
                state
            } else {
                val count = state.selectedQuantity - 1
                state.copy(
                    selectedQuantity = count,
                    product = toDetailProductUiModel(product, Quantity(count)),
                )
            }
        }
    }

    private fun toDetailProductUiModel(
        product: Product,
        quantity: Quantity,
    ): DetailProductUiModel {
        val price = product.price * quantity
        return DetailProductUiModel.of(
            name = product.name,
            price = price.amount,
            imageUrl = product.imageUrl,
            id = product.id,
            quantity = quantity.count,
        )
    }

    private fun toLatestProductUiModel(product: Product): LatestProductUiModel = LatestProductUiModel(
        id = product.id,
        title = product.name,
    )

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ShoppingApplication
                val container = application.container

                ProductDetailViewModel(
                    savedStateHandle = createSavedStateHandle(),
                    productRepository = container.productRepository,
                    cartRepository = container.cartRepository,
                    recentProductRepository = container.recentProductRepository,
                )
            }
        }
    }
}
