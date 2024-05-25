package woowacourse.shopping.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "productHistoryEntity")
data class ProductHistoryEntity(
    @PrimaryKey val productId: Long,
    val name: String,
    val price: Int,
    val imageUrl: String,
    val createAt: LocalDateTime,
)
