package woowacourse.shopping.model.products

import java.io.Serializable

data class ShoppingCartItem(
    val product: Product,
    val quantity: Int,
) : Serializable
