package woowacourse.shopping.data.product

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.shopping.domain.product.Product

@Entity(tableName = "cart")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    @ColumnInfo("image_url") val imageUrl: String,
    @ColumnInfo("name") val name: String,
    @ColumnInfo("price") val price: Int,
)

fun Product.toProductEntity(): ProductEntity {
    return ProductEntity(
        imageUrl = this.imageUrl,
        name = this.name,
        price = this.price,
    )
}
