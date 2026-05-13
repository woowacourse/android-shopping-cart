package woowacourse.shopping.ui.shopping.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import woowacourse.shopping.domain.PageRequest
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.Products
import woowacourse.shopping.domain.SHOPPING_PAGE_SIZE
import woowacourse.shopping.domain.toPage
import woowacourse.shopping.network.NetworkMonitor
import woowacourse.shopping.repository.cart.CartRepository
import woowacourse.shopping.repository.product.ProductRepository
import woowacourse.shopping.repository.recentviewedproduct.RecentlyViewedProductsRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class ProductListViewModel(
    private val recentViewedProductsRepository: RecentlyViewedProductsRepository,
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val networkMonitor: NetworkMonitor,
) : ViewModel() {
    // productRepository
    var currentPageIndex by mutableStateOf(0)
        private set

    fun increasePageIndex() {
        currentPageIndex++
    }

    // cartRepository
    fun getProductQuantity(productId: Uuid): Int = productQuantities[productId] ?: 0

    var totalProductQuantity by mutableIntStateOf(0)
        private set

    fun addProduct(
        product: Product,
        quantityToAdd: Int,
    ) {
        viewModelScope.launch {
            cartRepository.addProduct(product = product, quantityToAdd = quantityToAdd)
        }
    }

    fun decreaseProduct(
        productId: Uuid,
        quantityToRemove: Int,
    ) {
        viewModelScope.launch {
            cartRepository.decreaseProduct(
                productId = productId,
                quantityToRemove = quantityToRemove,
            )
        }
    }

    var products by mutableStateOf(Products())
        private set

    fun visibleProducts(): List<Product> =
        products.products
            .toPage(PageRequest(0, (currentPageIndex + 1) * SHOPPING_PAGE_SIZE))
            .items

    var productQuantities by mutableStateOf<Map<Uuid, Int>>(emptyMap())
        private set

    // recentlyViewedProducts

    var recentlyViewedProducts by mutableStateOf(Products())
        private set

    var isOnline by mutableStateOf(true)
        private set

    // Common
    init {
        viewModelScope.launch {
            networkMonitor.isOnline().collectLatest { isOnline ->
                this@ProductListViewModel.isOnline = isOnline
                if (isOnline) {
                    runCatching {
                        productRepository.refreshProducts()
                    }
                }
            }
        }

        viewModelScope.launch {
            productRepository.getAllProducts().collect { products ->
                this@ProductListViewModel.products = products
            }
        }
        viewModelScope.launch {
            cartRepository.getCartProducts().collect { cartProducts ->
                totalProductQuantity = cartProducts.sumOf { it.quantity }
                productQuantities = cartProducts.associate { it.productId to it.quantity }
            }
        }
        viewModelScope.launch {
            recentViewedProductsRepository
                .getRecentlyViewedProducts()
                .collect { recentlyViewedProducts ->
                    this@ProductListViewModel.recentlyViewedProducts = recentlyViewedProducts
                }
        }
    }
}
