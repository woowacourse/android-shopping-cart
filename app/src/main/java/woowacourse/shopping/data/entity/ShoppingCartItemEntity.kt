package woowacourse.shopping.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_cart_item")
data class ShoppingCartItemEntity(
    val id: Long = 0,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "product_id") val productId: Long,
    val quantity: Int,
)
