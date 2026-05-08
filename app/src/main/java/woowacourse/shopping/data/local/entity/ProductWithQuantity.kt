package woowacourse.shopping.data.local.entity

import woowacourse.shopping.domain.Product

data class ProductWithQuantity(
    val product: Product,
    val quantity: Int,
)
