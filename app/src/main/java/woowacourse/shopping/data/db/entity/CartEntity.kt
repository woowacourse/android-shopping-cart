package woowacourse.shopping.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "Cart")
data class CartEntity(
    @PrimaryKey val id: Long,
    val count: Int,
)