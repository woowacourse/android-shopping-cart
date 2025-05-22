package woowacourse.shopping.data.cart

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_item")
data class CartItemEntity(
    @PrimaryKey val id: Int,
    val quantity: Int,
)
