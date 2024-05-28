package woowacourse.shopping.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "cartProductEntity")
data class CartProductEntity(
    @PrimaryKey val productId: Long,
    val name: String,
    val price: Int,
    val quantity: Int,
    val imageUrl: String,
    val createAt: LocalDateTime,
)
