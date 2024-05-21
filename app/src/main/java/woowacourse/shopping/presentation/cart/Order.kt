package woowacourse.shopping.presentation.cart

import woowacourse.shopping.data.model.Product

data class Order(
    val cartItemId: Long,
    val quantity: Int,
    val product: Product,
)
