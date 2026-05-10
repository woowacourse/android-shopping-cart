package woowacourse.shopping.ui.stateholder

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.retain.retain
import androidx.compose.runtime.setValue
import kotlinx.coroutines.runBlocking
import woowacourse.shopping.MockCatalog
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.CartProducts
import woowacourse.shopping.domain.Product
import woowacourse.shopping.repository.CatalogProductRepository
import woowacourse.shopping.repository.ProductRepository
import java.util.UUID

data class CatalogItemUiState(
    val product: Product,
    val quantity: Int,
)

class CatalogScreenStateHolder(
    private val productRepository: ProductRepository,
    initialCart: Cart = Cart(CartProducts(emptyList())),
) {
    private val _catalog = mutableStateOf(emptyList<Product>())

    var cart by mutableStateOf(initialCart)
        private set

    val uiStates: List<CatalogItemUiState>
        get() = _catalog.value.map { product ->
            CatalogItemUiState(
                product = product,
                quantity = getQuantity(product.productId)
            )
        }

    private var recentItemIndex = 0

    init {
        loadProducts()
    }

    fun updateCart(newCart: Cart) {
        cart = newCart
    }

    fun onIncrease(id: UUID) {
        val product = _catalog.value.find { it.productId == id }
        if (product != null) {
            cart = cart.addProduct(product)
        }
    }

    fun onDecrease(id: UUID) {
        val cartProduct = cart.cartProducts.findSameProduct(id)
        if (cartProduct != null) {
            cart = if (cartProduct.amount > 1) {
                cart.decreaseProduct(id)
            } else {
                cart.removeProduct(id)
            }
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
    initialCart: Cart
): CatalogScreenStateHolder =
    retain(Unit) {
        CatalogScreenStateHolder(repository, initialCart)
    }

