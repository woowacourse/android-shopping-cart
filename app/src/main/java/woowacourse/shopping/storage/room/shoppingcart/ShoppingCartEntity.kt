package woowacourse.shopping.storage.room.shoppingcart

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import woowacourse.shopping.storage.room.shoppingItem.ShoppingItemEntity

@Entity(
    tableName = "shopping_cart_items",
    foreignKeys = [
        ForeignKey(
            entity = ShoppingItemEntity::class,
            parentColumns = ["product_id"],
            childColumns = ["product_id"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [
        Index(value = ["product_id"], unique = true),
    ],
)
data class ShoppingCartEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0L,
    @ColumnInfo(name = "product_id")
    val productId: Long,
)
