package woowacourse.shopping.ui.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.RecentProductRepository
import java.util.UUID

class CatalogViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val recentProductRepository: RecentProductRepository,
) : ViewModel() {
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    val cart: StateFlow<Cart> = cartRepository.cartFlow

    val catalogItems: StateFlow<List<CatalogItemUiState>> =
        combine(products, cart) { products, cart ->
            products.map { product ->
                CatalogItemUiState(
                    product = product,
                    quantity = cart.cartProducts.findSameProduct(product.productId)?.amount ?: 0
                )
            }
        }.stateIn(viewModelScope, SharingStarted.Companion.WhileSubscribed(5000), emptyList())

    val recentProducts: StateFlow<List<Product>> = recentProductRepository.recentProducts
        .stateIn(viewModelScope, SharingStarted.Companion.WhileSubscribed(5000), emptyList())

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

    companion object {
        private const val PAGE_SIZE = 20

        fun provideFactory(
            productRepository: ProductRepository,
            cartRepository: CartRepository,
            recentProductRepository: RecentProductRepository,
        ): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T: ViewModel> create(modelClass: Class<T>): T {
                    return CatalogViewModel(
                        productRepository,
                        cartRepository,
                        recentProductRepository
                    ) as T
                }
            }
    }
}
