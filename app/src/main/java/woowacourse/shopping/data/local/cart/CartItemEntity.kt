package woowacourse.shopping.data.local.cart

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItemEntity(
    @PrimaryKey val productId: Int,
    val name: String,
    val price: Int,
    val imageUrl: String,
    val quantity: Int,
)
