package woowacourse.shopping.ui.productdetail.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import woowacourse.shopping.data.local.AppDatabase
import woowacourse.shopping.data.remote.source.ProductRemoteDataSource
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.data.repository.RecentProductRepositoryImpl
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.Quantity
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.RecentProductRepository
import woowacourse.shopping.ui.model.DetailProductUiModel
import woowacourse.shopping.ui.model.LatestProductUiModel
import woowacourse.shopping.ui.productdetail.state.ProductDetailUiState

class ProductDetailViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val recentProductRepository: RecentProductRepository,
) : ViewModel() {
    private val productId: String = savedStateHandle["product_id"] ?: ""

    private val _uiState = MutableStateFlow(ProductDetailUiState())
    val uiState = _uiState.asStateFlow()

    private var currentProduct: Product? = null

    private var cart = Cart()

    init {
        observeCart()
        loadProduct()
    }

    private fun observeCart() {
        cartRepository.getCart()
            .onEach { cartItems ->
                cart = Cart(cartItems)
            }.launchIn(viewModelScope)
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

        val updatedItem = cart.plusProduct(product, quantity).findCartItemById(productId) ?: return
        viewModelScope.launch {
            cartRepository.updateCartItem(updatedItem)
        }
    }

    fun increment() {
        _uiState.update { state ->
            val count = state.selectedQuantity + 1
            state.copy(
                selectedQuantity = count,
                product = toDetailProductUiModel(currentProduct!!, Quantity(count)),
            )
        }
    }

    fun decrement() {
        _uiState.update { state ->
            if (state.selectedQuantity == 1) {
                state
            } else {
                val count = state.selectedQuantity - 1
                state.copy(
                    selectedQuantity = count,
                    product = toDetailProductUiModel(currentProduct!!, Quantity(count)),
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
                val context = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]!!
                val database = AppDatabase.getDatabase(context)
                val dataSource = ProductRemoteDataSource()

                val productRepository = ProductRepositoryImpl(dataSource)
                val cartRepository = CartRepositoryImpl(database.cartDao())
                val recentProductRepository = RecentProductRepositoryImpl(database.recentProductDao())

                val savedStateHandle = createSavedStateHandle()
                ProductDetailViewModel(
                    savedStateHandle = savedStateHandle,
                    productRepository = productRepository,
                    cartRepository = cartRepository,
                    recentProductRepository = recentProductRepository,
                )
            }
        }
    }
}
