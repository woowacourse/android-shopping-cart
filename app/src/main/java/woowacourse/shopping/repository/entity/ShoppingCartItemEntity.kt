package woowacourse.shopping.repository.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "shopping_cart_items",
    foreignKeys = [
        ForeignKey(
            entity = ProductEntity::class,
            parentColumns = ["id"],
            childColumns = ["product_id"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
)
data class ShoppingCartItemEntity(
    @PrimaryKey val id: String,
    @ColumnInfo val quantity: Int,
    @ColumnInfo("product_id") val productId: String,
)
