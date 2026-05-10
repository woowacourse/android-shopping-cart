package woowacourse.shopping.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartEntity(
    @PrimaryKey val productId: String,
    val productName: String,
    val productImageUrl: String,
    val price: Int,
    val quantity: Int,
)
