package woowacourse.shopping.domain.cart

import java.io.Serializable
import woowacourse.shopping.domain.product.Product

data class CartProduct(
    val id: Long? = null,
    val product: Product,
    val quantity: Int,
) : Serializable

