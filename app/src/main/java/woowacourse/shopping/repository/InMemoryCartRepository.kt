package woowacourse.shopping.repository

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.ProductAndCount
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class InMemoryCartRepository : CartRepository {
    private var cart by mutableStateOf(Cart())

    override fun getCartProducts(): List<ProductAndCount> = cart.productAndCounts

    override fun addProduct(product: Product) {
        cart = cart.addProductToCart(product)
    }

    override fun deleteProduct(productId: Uuid) {
        cart = cart.deleteProductFromCart(productId)
    }
}