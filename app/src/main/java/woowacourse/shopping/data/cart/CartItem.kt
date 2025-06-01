package woowacourse.shopping.data.cart

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CartItemEntity")
data class CartItem(
    @PrimaryKey(autoGenerate = true) val uid: Long = 0,
    val id: Long,
    val imageUrl: String,
    val name: String,
    val price: Int,
    val quantity: Int,
)
