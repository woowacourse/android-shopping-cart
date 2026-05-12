package woowacourse.shopping.domain.model.cart

import kotlinx.serialization.Serializable
import woowacourse.shopping.domain.model.Quantity
import woowacourse.shopping.domain.model.product.Product

@Serializable
data class CartItem(
    val product: Product,
    val quantity: Quantity,
)
