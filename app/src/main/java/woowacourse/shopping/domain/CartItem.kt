package woowacourse.shopping.domain

import java.io.Serializable

data class CartItem(
    val product: Product,
    val quantity: Int,
) : Serializable
