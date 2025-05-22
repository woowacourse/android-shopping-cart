package woowacourse.shopping.model.cart

import woowacourse.shopping.model.product.Product
import java.io.Serializable

data class CartItem(
    val product: Product,
    val quantity: Int = 1,
) : Serializable
