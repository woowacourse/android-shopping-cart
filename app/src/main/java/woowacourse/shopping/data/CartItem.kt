package woowacourse.shopping.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CartItemEntity")
data class CartItem(
    @PrimaryKey(autoGenerate = true) val uid: Long = 0,
    val imageUrl: String,
    val name: String,
    val price: Int,
    val quantity: Int,
)
