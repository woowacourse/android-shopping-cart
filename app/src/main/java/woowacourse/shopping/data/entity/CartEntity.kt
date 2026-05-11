package woowacourse.shopping.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "cart_items",
    foreignKeys = [
        ForeignKey(
            entity = CatalogEntity::class,
            parentColumns = ["productId"],
            childColumns = ["productId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class CartEntity(
    @PrimaryKey val productId: UUID,
    val amount: Int,
    val addedAt: Long = System.currentTimeMillis()
)
