package woowacourse.shopping.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "cart_items")
data class CartEntity(
    @PrimaryKey val productId: UUID,
    val quantity: Int,
)
