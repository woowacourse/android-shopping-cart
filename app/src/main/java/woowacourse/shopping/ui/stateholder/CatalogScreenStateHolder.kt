package woowacourse.shopping.ui.stateholder

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.retain.retain
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.Product
import woowacourse.shopping.repository.CartRepository
import woowacourse.shopping.repository.InMemoryCartRepository
import woowacourse.shopping.repository.ProductRepository
import java.util.UUID

data class CatalogItemUiState(
    val product: Product,
    val quantity: Int,
)

class CatalogScreenStateHolder(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository = InMemoryCartRepository,
    private val coroutineScope: CoroutineScope,
) {
    private val _catalog = mutableStateOf(emptyList<Product>())

    var cart by mutableStateOf(cartRepository.cart)
        private set

    init {
        coroutineScope.launch {
            cartRepository.cartFlow.collect { newCart ->
                cart = newCart
            }
        }
        loadProducts()
    }

    val uiStates: List<CatalogItemUiState>
        get() = _catalog.value.map { product ->
            CatalogItemUiState(
                product = product,
                quantity = getQuantity(product.productId)
            )
        }

    private var recentItemIndex = 0

    fun onIncrease(id: UUID) {
        val product = _catalog.value.find { it.productId == id }
        if (product != null) {
            coroutineScope.launch {
                cartRepository.addProduct(product)
            }
        }
    }

    fun onDecrease(id: UUID) {
        coroutineScope.launch {
            cartRepository.decreaseProduct(id)
        }
    }

    fun getQuantity(id: UUID): Int {
        return cart.cartProducts.findSameProduct(id)?.amount ?: 0
    }

    fun onLoadClick() {
        recentItemIndex++
        loadProducts()
    }

    private fun loadProducts() {
        val products = runBlocking {
            productRepository.getProducts(recentItemIndex, MAX_PRODUCT)
        }
        _catalog.value += products
    }

    companion object {
        const val MAX_PRODUCT = 20
    }
}

@Composable
fun retainCatalogScreenStateHolder(
    repository: ProductRepository,
): CatalogScreenStateHolder {
    val scope = rememberCoroutineScope()
    return retain(Unit) {
        CatalogScreenStateHolder(repository, coroutineScope = scope)
    }
}
