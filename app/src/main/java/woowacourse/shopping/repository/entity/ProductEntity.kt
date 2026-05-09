package woowacourse.shopping.repository.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.shopping.model.Price
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.ProductTitle

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo val name: String,
    @ColumnInfo val price: Int,
    @ColumnInfo("image_url") val imageUrl: String,
)

fun ProductEntity.toModel() =
    Product(
        id = id.toString(),
        title = ProductTitle(name),
        price = Price(price),
        imageUrl = imageUrl,
    )
