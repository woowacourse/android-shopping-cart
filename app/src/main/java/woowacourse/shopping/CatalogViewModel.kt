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
import woowacourse.shopping.repository.cartRepository.CartRepository
import woowacourse.shopping.repository.productRepository.CatalogProductRepository
import woowacourse.shopping.repository.cartRepository.InMemoryCartRepository
import java.util.UUID

class CatalogViewModel(
    private val productRepository: CatalogProductRepository = CatalogProductRepository,
    private val cartRepository: CartRepository = InMemoryCartRepository,
) : ViewModel() {
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    val cart: StateFlow<Cart> = cartRepository.cartFlow

    val recentProducts: StateFlow<List<Product>> = MutableStateFlow(
            MockCatalog.catalog.subList(0, 5)
    )


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
        }
    }

    fun decreaseProductInCart(productId: UUID) {
        viewModelScope.launch {
            cartRepository.decreaseProduct(productId)
        }
    }

    fun removeProductFromCart(productId: UUID) {
        viewModelScope.launch {
            cartRepository.removeProduct(productId)
        }
    }

    fun getQuantity(id: UUID): Int {
        return cart.value.cartProducts.findSameProduct(id)?.amount ?: 0
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}
