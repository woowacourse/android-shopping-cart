package woowacourse.shopping.data.local.entity

import androidx.room.Entity
import woowacourse.shopping.domain.Product

data class ProductWithQuantity(
    val product: Product,
    val quantity: Int
)
