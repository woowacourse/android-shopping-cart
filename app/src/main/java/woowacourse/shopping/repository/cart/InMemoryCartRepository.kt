package woowacourse.shopping.repository.cart

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.ProductWithQuantity
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class InMemoryCartRepository : CartRepository {
    private var cart by mutableStateOf(Cart())

    override fun getTotalProductQuantity(): Int =
        cart.productsWithQuantity.sumOf { it.quantity }

    override fun getProductQuantity(productId: Uuid): Int =
        cart.productsWithQuantity.firstOrNull { it.productId == productId }?.quantity ?: 0

    override fun getCartProducts(): List<ProductWithQuantity> = cart.productsWithQuantity

    override fun addProduct(product: Product, quantityToAdd: Int) {
        cart = cart.addProductToCart(ProductWithQuantity(product, quantityToAdd))
    }

    override fun deleteProduct(productId: Uuid) {
        cart = cart.deleteProductFromCart(productId = productId)
    }

    override fun decreaseProduct(productId: Uuid, quantityToRemove: Int) {
        cart = cart.decreaseProductQuantity(productId = productId, quantity = quantityToRemove)
    }
}