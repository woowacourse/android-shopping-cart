package woowacourse.shopping.domain

import java.util.Collections.emptyList
import kotlin.math.min
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

val CART_PAGE_SIZE = 5

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

    fun getProductAndCounts(
        page: Int,
        pageSize: Int = CART_PAGE_SIZE,
    ): List<CartItem> {
        val fromIndex = page * pageSize
        val toIndex = min(fromIndex + CART_PAGE_SIZE, cartItems.size)
        return cartItems.subList(fromIndex, toIndex)
    }
}
