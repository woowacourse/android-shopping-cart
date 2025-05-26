package woowacourse.shopping.data.product.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.shopping.domain.product.Product

@Entity(tableName = "product")
data class ProductEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val price: Int,
    val imageUrl: String,
) {
    fun toDomain(): Product =
        Product(
            id = id,
            name = name,
            price = price,
            imageUrl = imageUrl,
        )
}

fun Product.toEntity(): ProductEntity =
    ProductEntity(
        id = id,
        name = name,
        price = price,
        imageUrl = imageUrl,
    )
