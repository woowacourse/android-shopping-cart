package woowacourse.shopping.domain.model

import java.io.Serializable

data class CartItem(
    val product: Product,
    val quantity: Int,
) : Serializable
