package woowacourse.shopping.repository.cart

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.ProductWithQuantity
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class InMemoryCartRepository : CartRepository {
    private var cart by mutableStateOf(Cart())
    private val cartProductsFlow = MutableStateFlow(cart.productsWithQuantity)

    override suspend fun getTotalProductQuantity(): Int = cart.productsWithQuantity.sumOf { it.quantity }

    override suspend fun getProductQuantity(productId: Uuid): Int =
        cart.productsWithQuantity.firstOrNull { it.productId == productId }?.quantity ?: 0

    override fun getCartProducts(): Flow<List<ProductWithQuantity>> = cartProductsFlow.asStateFlow()

    override suspend fun addProduct(
        product: Product,
        quantityToAdd: Int,
    ) {
        cart = cart.addProductToCart(ProductWithQuantity(product, quantityToAdd))
        cartProductsFlow.value = cart.productsWithQuantity
    }

    override suspend fun deleteProduct(productId: Uuid) {
        cart = cart.deleteProductFromCart(productId = productId)
        cartProductsFlow.value = cart.productsWithQuantity
    }

    override suspend fun decreaseProduct(
        productId: Uuid,
        quantityToRemove: Int,
    ) {
        cart = cart.decreaseProductQuantity(productId = productId, quantity = quantityToRemove)
        cartProductsFlow.value = cart.productsWithQuantity
    }
}
