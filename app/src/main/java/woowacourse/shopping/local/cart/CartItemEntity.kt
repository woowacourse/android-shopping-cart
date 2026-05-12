package woowacourse.shopping.local.cart

import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.shopping.model.CartItem
import woowacourse.shopping.model.ProductId
import java.util.UUID

@Entity(tableName = "cart_items")
data class CartItemEntity(
    @PrimaryKey val productId: String,
    val quantity: Int,
    val createdAtMillis: Long,
) {
    fun toDomain(): CartItem =
        CartItem(
            productId = ProductId(UUID.fromString(productId)),
            quantity = quantity,
        )

    companion object {
        fun fromDomain(
            cartItem: CartItem,
            createdAtMillis: Long,
        ): CartItemEntity =
            CartItemEntity(
                productId = cartItem.productId.value.toString(),
                quantity = cartItem.quantity,
                createdAtMillis = createdAtMillis,
            )
    }
}
