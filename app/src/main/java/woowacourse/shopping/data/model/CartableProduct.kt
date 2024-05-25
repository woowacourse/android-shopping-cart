package woowacourse.shopping.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class CartableProduct(
    @Embedded val product: Product,
    @Relation(
        parentColumn = "id",
        entityColumn = "productId",
    )
    val cartItem: CartItem? = null,
) {
    val quantity: Int
        get() = cartItem?.quantity ?: 0
}
