package woowacourse.shopping.ui.productlist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
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
import woowacourse.shopping.ui.model.SimpleProductUiModel
import woowacourse.shopping.ui.productlist.state.ProductListUiEvent
import woowacourse.shopping.ui.productlist.state.ProductListUiState

class ProductListViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val recentProductRepository: RecentProductRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProductListUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<ProductListUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var currentPage = 1
    private val _products = mutableListOf<Product>()
    private var cart = Cart()

    init {
        observeDatabase()
        loadInitData()
    }

    private fun observeDatabase() {
        combine(
            cartRepository.getCart(),
            recentProductRepository.getRecentProducts(),
        ) { cartItems, recentProducts ->
            cart = Cart(cartItems)

            _uiState.update { state ->
                state.copy(
                    products = _products.map { product ->
                        toDetailProductUiModel(product, cart.getQuantity(product) ?: Quantity(0))
                    },
                    recentProducts = recentProducts.map { toSimpleProductUiModel(it) },
                    cartCount = cart.totalQuantity.count,
                )
            }
        }.launchIn(viewModelScope)
    }

    private fun loadInitData() {
        viewModelScope.launch {
            productRepository.getProducts(page = currentPage, size = PAGE_SIZE)
                .onSuccess { products ->
                    _products.addAll(products)
                    currentPage++
                    syncUiState(products.size < PAGE_SIZE)
                }
                .onFailure { exception ->
                    _uiState.update { it.copy(isError = true, errorMessage = exception.message) }
                    _uiEvent.send(ProductListUiEvent.ShowToast("상품 로드에 실패했습니다."))
                }
        }
    }

    fun fetchProducts(pageSize: Int = PAGE_SIZE) {
        require(pageSize > 0) { "PAGE_SIZE의 크기는 0보다 커야한다" }

        if (_uiState.value.isLoading || _uiState.value.isEnd) return

        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            productRepository.getProducts(page = currentPage, size = pageSize)
                .onSuccess { newProducts ->
                    _products.addAll(newProducts)

                    val isEndOfList = newProducts.size < pageSize

                    if (!isEndOfList) {
                        currentPage++
                    }

                    syncUiState(isEndOfList)
                }
                .onFailure { exception ->
                    _uiState.update {
                        it.copy(
                            isError = true,
                            errorMessage = exception.message,
                            isLoading = false,
                        )
                    }
                }
        }
    }

    fun addCartItem(productId: String) {
        val product = _products.find { it.hasId(productId) } ?: return
        val updatedItem = cart.plusProduct(product, Quantity(1)).findCartItemById(productId) ?: return

        viewModelScope.launch {
            cartRepository.updateCartItem(updatedItem)
        }
    }

    fun removeCartItem(productId: String) {
        val product = _products.find { it.hasId(productId) } ?: return
        val updatedItem = cart.minusProduct(product, Quantity(1)).findCartItemById(productId)

        viewModelScope.launch {
            if (updatedItem == null) {
                cartRepository.deleteCartItem(productId)
            } else {
                cartRepository.updateCartItem(updatedItem)
            }
        }
    }

    private fun syncUiState(isEnd: Boolean) {
        _uiState.update { state ->
            state.copy(
                products = _products.map { product ->
                    toDetailProductUiModel(product, cart.getQuantity(product) ?: Quantity(0))
                },
                isEnd = isEnd,
            )
        }
    }

    private fun toDetailProductUiModel(
        product: Product,
        quantity: Quantity,
    ): DetailProductUiModel = DetailProductUiModel.of(
        name = product.name,
        price = product.price.amount,
        imageUrl = product.imageUrl,
        id = product.id,
        quantity = quantity.count,
    )

    private fun toSimpleProductUiModel(product: Product): SimpleProductUiModel = SimpleProductUiModel(
        id = product.id,
        imageUrl = product.imageUrl,
        title = product.name,
    )

    companion object {
        private const val PAGE_SIZE = 20

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ShoppingApplication
                val container = application.container

                ProductListViewModel(
                    productRepository = container.productRepository,
                    cartRepository = container.cartRepository,
                    recentProductRepository = container.recentProductRepository,
                )
            }
        }
    }
}
