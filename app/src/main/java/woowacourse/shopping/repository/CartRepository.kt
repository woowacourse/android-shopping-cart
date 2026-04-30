package woowacourse.shopping.repository

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import woowacourse.shopping.domain.CART_PAGE_SIZE
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.ProductAndCount
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

object CartRepository {
    private var cart by mutableStateOf(Cart())

    fun getCartProducts(): List<ProductAndCount> = cart.productAndCounts

    fun getProductAndCounts(
        page: Int,
        pageSize: Int = CART_PAGE_SIZE,
    ): List<ProductAndCount> = cart.getProductAndCounts(page, pageSize)

    @OptIn(ExperimentalUuidApi::class)
    fun addProduct(product: Product) {
        cart = cart.addProductToCart(product)
    }

    @OptIn(ExperimentalUuidApi::class)
    fun deleteProduct(productId: Uuid) {
        cart = cart.deleteProductFromCart(productId)
    }
}
