package woowacourse.shopping.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItemEntity(
    val id: Long = 0L,
    @PrimaryKey val productId: Long,
    val productName: String,
    val price: Long,
    val imgUrl: String,
    val quantity: Int,
)
