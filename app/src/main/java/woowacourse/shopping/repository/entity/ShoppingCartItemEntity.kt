package woowacourse.shopping.repository.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "shopping_cart_items",
    indices = [
        Index(value = ["product_id"], unique = true),
    ],
)
data class ShoppingCartItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo val quantity: Int,
    @ColumnInfo("product_id") val productId: String,
)
