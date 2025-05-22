package woowacourse.shopping.model.cart

import woowacourse.shopping.model.product.Product

data class CartItem(
    val product: Product,
    val quantity: Int = 1,
)
