package woowacourse.shopping.data.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cartProductEntity")
data class CartProductEntity(
    @PrimaryKey val productId: Long,
    val name: String,
    val price: Int,
    val quantity: Int,
    val imageUrl: String,
)
