package woowacourse.shopping.domain.model.cart

import woowacourse.shopping.domain.model.product.Product
import java.util.Collections.emptyList
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class Cart(
    val cartItems: List<CartItem> = emptyList(),
) {
    fun addProductToCart(product: Product): Cart {
        val index = cartItems.indexOfFirst { product.productId == it.product.productId }
        if (index != -1) {
            val existProductAndCount = cartItems[index]
            val newProductAndCount = existProductAndCount.increaseQuantity()
            val updated = cartItems.toMutableList()
            updated[index] = newProductAndCount
            return copy(cartItems = updated)
        } else {
            return copy(cartItems = cartItems + CartItem(product, 1))
        }
    }

    fun deleteProductFromCart(productId: Uuid): Cart =
        copy(
            cartItems =
                cartItems.filterNot {
                    it.productId == productId
                },
        )
}
