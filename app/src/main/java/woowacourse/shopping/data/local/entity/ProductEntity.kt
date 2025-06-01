package woowacourse.shopping.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.shopping.domain.product.Money
import woowacourse.shopping.domain.product.Product

@Entity(tableName = "cart")
data class ProductEntity(
    @PrimaryKey val id: Long,
    @ColumnInfo("image_url") val imageUrl: String,
    @ColumnInfo("name") val name: String,
    @ColumnInfo("price") val price: Int,
) {
    fun toDomain(): Product = Product(
        id = id,
        imageUrl = imageUrl,
        name = name,
        _price = Money(price)
    )
}
