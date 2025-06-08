package woowacourse.shopping.data.product

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.shopping.data.product.ProductEntity.Companion.PRODUCT_TABLE_NAME

@Entity(tableName = PRODUCT_TABLE_NAME)
data class ProductEntity(
    @PrimaryKey val id: Int = 0,
    val name: String,
    val price: Int,
    val quantity: Int,
    @ColumnInfo(name = "image_url") val imageUrl: String,
) {
    companion object {
        const val PRODUCT_TABLE_NAME = "products"
    }
}
