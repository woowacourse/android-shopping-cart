package woowacourse.shopping.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity

@Entity
data class CartProduct(
    @Embedded
    val product: Product,
    @Embedded
    val cart: Cart
)
