package woowacourse.shopping.data.cartRepository

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
data class CartEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    @ColumnInfo(name = "product_id") val productId: Long,
    @ColumnInfo(name = "product_name") val name: String,
    @ColumnInfo(name = "product_imageUrl") val imageUrl: String,
    @ColumnInfo(name = "product_price") val price: Int,
)
