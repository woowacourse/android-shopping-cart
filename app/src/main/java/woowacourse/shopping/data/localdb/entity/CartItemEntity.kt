package woowacourse.shopping.data.localdb.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItemEntity(
    @PrimaryKey val id: String,
    val quantity: Int,
    val timestamp: Long,
)
