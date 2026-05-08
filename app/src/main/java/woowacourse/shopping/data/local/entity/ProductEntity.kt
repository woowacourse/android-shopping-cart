package woowacourse.shopping.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.shopping.domain.Price

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey val productId: String,
    @ColumnInfo(name = "image_url") val imageUrl: String,
    @ColumnInfo(name = "product_name") val productName: String,
    @ColumnInfo(name = "price") val price: Price,
)
