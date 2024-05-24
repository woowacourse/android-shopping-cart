package woowacourse.shopping.db.cartItem

import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.shopping.model.CartItem

@Entity(tableName = "cart_items")
data class CartItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val productId: Int,
    var quantity: Int
) {
    fun toCartItem(): CartItem {
        return CartItem(
            productId = this.productId,
            quantity = this.quantity
        )
    }

}
