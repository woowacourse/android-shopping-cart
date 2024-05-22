package woowacourse.shopping.presentation.uistate

import woowacourse.shopping.data.model.Product

data class Order(
    val cartItemId: Long,
    val quantity: Int,
    val product: Product,
)
