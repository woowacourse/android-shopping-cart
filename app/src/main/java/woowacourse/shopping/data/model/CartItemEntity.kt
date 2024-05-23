package woowacourse.shopping.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.shopping.data.db.cartItem.CartItemDatabase.Companion.CART_ITEMS_DB_NAME
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Product

@Entity(tableName = CART_ITEMS_DB_NAME)
data class CartItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val product: Product,
    val quantity: Int,
) {
    fun toCartItem(): CartItem {
        return CartItem(
            id = id,
            product = product,
            quantity = quantity,
        )
    }

    companion object {
        fun makeCartItemEntity(
            product: Product,
            quantity: Int,
        ): CartItemEntity {
            return CartItemEntity(product = product, quantity = quantity)
        }
    }
}
