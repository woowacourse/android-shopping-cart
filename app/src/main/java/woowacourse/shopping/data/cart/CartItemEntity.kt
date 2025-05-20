package woowacourse.shopping.data.cart

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
data class CartItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    @ColumnInfo("product_id") val productId: Long,
)
