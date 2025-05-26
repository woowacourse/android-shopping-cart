package woowacourse.shopping.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_item")
data class CartItemEntity(
    @PrimaryKey val id: Long,
    @ColumnInfo("quantity") val quantity: Int,
)
