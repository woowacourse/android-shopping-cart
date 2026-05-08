package woowacourse.shopping.ui.shopping

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import woowacourse.shopping.model.ProductId
import woowacourse.shopping.repository.CartRepository
import woowacourse.shopping.repository.ProductRepository
import woowacourse.shopping.repository.inmemory.InMemoryCartRepository
import woowacourse.shopping.repository.inmemory.InMemoryProductRepository

private const val PAGE_SIZE = 20

class ShoppingViewModel(
    private val productRepository: ProductRepository = InMemoryProductRepository,
    private val cartRepository: CartRepository = InMemoryCartRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ShoppingUiState(isLoading = true))
    val uiState: StateFlow<ShoppingUiState> = _uiState.asStateFlow()

    private var visibleCount = PAGE_SIZE

    init {
        loadProducts()
    }

    fun reload() {
        if (_uiState.value.isLoading) return
        viewModelScope.launch {
            refreshProducts()
        }
    }

    private fun loadProducts() {
        viewModelScope.launch {
            refreshProducts()
        }
    }

    suspend fun refreshProducts() {
        _uiState.value = _uiState.value.copy(isLoading = true)

        val visibleProducts = productRepository.getProducts(0, visibleCount).toList()
        val hasNext = productRepository.hasNext(visibleProducts.count() - 1)
        val cartItems = cartRepository.getCartItems(0, cartRepository.count())
        val cartQuantity = cartItems.sumOf { it.quantity }

        val visibleCartItems = cartRepository.getCartItemsByProductIds(visibleProducts.map { it.id }.toSet())
        val quantityByProductId = visibleCartItems.associate { it.productId to it.quantity }

        val products =
            visibleProducts.map { product ->
                ShoppingProductUiState(
                    product = product,
                    quantity = quantityByProductId[product.id] ?: 0,
                )
            }

        _uiState.value =
            ShoppingUiState(
                products = products,
                cartQuantity = cartQuantity,
                hasNext = hasNext,
                isLoading = false,
            )
    }

    fun loadMore() {
        val currentState = _uiState.value
        if (currentState.isLoading || !currentState.hasNext) return
        visibleCount = minOf(visibleCount + PAGE_SIZE, productRepository.size)
        loadProducts()
    }

    fun addToCart(productId: ProductId) = increaseQuantity(productId)

    fun increaseQuantity(productId: ProductId) {
        if (_uiState.value.isLoading) return
        viewModelScope.launch {
            cartRepository.add(productId)
            refreshProducts()
        }
    }

    fun decreaseQuantity(productId: ProductId) {
        if (_uiState.value.isLoading) return
        viewModelScope.launch {
            cartRepository.delete(productId)
            refreshProducts()
        }
    }
}
