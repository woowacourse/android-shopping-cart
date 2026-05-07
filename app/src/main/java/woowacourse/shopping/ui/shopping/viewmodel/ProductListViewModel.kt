package woowacourse.shopping.ui.shopping.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.PageRequest
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.SHOPPING_PAGE_SIZE
import woowacourse.shopping.domain.toPage
import woowacourse.shopping.repository.cart.CartRepository
import woowacourse.shopping.repository.product.ProductRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class ProductListViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
) : ViewModel() {
    // productRepository
    var currentPageIndex by mutableStateOf(0)
        private set

    fun visibleProducts(): List<Product> =
        productRepository
            .getAllProducts()
            .products
            .toPage(PageRequest(0, (currentPageIndex + 1) * SHOPPING_PAGE_SIZE))
            .items

    fun increasePageIndex() {
        currentPageIndex++
    }

    // cartRepository
    fun getProductQuantity(productId: Uuid): Int = cartRepository.getProductQuantity(productId = productId)

    val totalProductQuantity: Int
        get() = cartRepository.getCartProducts().sumOf { it.quantity }

    fun addProduct(
        product: Product,
        quantityToAdd: Int,
    ) {
        cartRepository.addProduct(product = product, quantityToAdd = quantityToAdd)
    }

    fun decreaseProduct(
        productId: Uuid,
        quantityToRemove: Int,
    ) {
        cartRepository.decreaseProduct(productId = productId, quantityToRemove = quantityToRemove)
    }
}
