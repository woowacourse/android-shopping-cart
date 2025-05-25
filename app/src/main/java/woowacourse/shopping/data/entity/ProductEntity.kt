package woowacourse.shopping.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.shopping.domain.model.Product

@Entity(tableName = "product")
data class ProductEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "imageUrl") val imageUrl: String,
    @ColumnInfo(name = "price") val price : Int
)

fun ProductEntity.toDomain(): Product {
    return Product(
        id = id,
        name = name,
        price = price,
        imageUrl = imageUrl
    )
}

fun Product.toEntity(): ProductEntity {
    return ProductEntity(
        id = id,
        name = name,
        price = price,
        imageUrl = imageUrl
    )
}