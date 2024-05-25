package woowacourse.shopping.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class CartedProduct(
    @Embedded val cartItem: CartItem,
    @Relation(
        parentColumn = "productId",
        entityColumn = "id",
    )
    val product: Product,
)
