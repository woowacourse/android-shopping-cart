package woowacourse.shopping

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.Product
import woowacourse.shopping.repository.CartRepository
import woowacourse.shopping.repository.CatalogProductRepository
import java.util.UUID

class CatalogViewModel(
    private val productRepository: CatalogProductRepository,
    private val cartRepository: CartRepository,
) : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    private val _cart = MutableStateFlow(cartRepository.cart)
    val cart: StateFlow<Cart> = _cart.asStateFlow()

    private var currentPage = 0

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            val newProducts = productRepository.getProducts(currentPage, PAGE_SIZE)
            _products.update { it + newProducts }
            currentPage++
        }
    }

    fun addProductToCart(product: Product) {
        viewModelScope.launch {
            cartRepository.addProduct(product)
            _cart.value = cartRepository.cart
        }
    }

    fun decreaseProductInCart(productId: UUID) {
        viewModelScope.launch {
            cartRepository.decreaseProduct(productId)
            _cart.value = cartRepository.cart
        }
    }

    fun removeProductFromCart(productId: UUID) {
        viewModelScope.launch {
            cartRepository.removeProduct(productId)
            _cart.value = cartRepository.cart
        }
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}
