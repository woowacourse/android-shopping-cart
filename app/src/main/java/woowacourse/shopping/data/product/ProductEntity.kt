package woowacourse.shopping.data.product

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "productEntity")
data class ProductEntity(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "product_imageUrl") val imageUrl: String,
    @ColumnInfo(name = "product_name") val name: String,
    @ColumnInfo(name = "product_price") val price: Int,
)
