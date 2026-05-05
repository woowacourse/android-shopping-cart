package woowacourse.shopping.domain.model.cart

import woowacourse.shopping.domain.model.Quantity
import woowacourse.shopping.domain.model.product.Product

data class CartItem(
    val product: Product,
    val quantity: Quantity,
)
