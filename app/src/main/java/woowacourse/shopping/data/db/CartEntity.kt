package woowacourse.shopping.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.shopping.domain.Product

@Entity(tableName = "cart")
data class CartEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    @ColumnInfo(name = "product_name") val name: String,
    @ColumnInfo(name = "product_imageUrl") val imageUrl: String,
    @ColumnInfo(name = "product_price") val price: Int,
) {
    companion object {
        fun from(product: Product): CartEntity =
            CartEntity(
                id = product.id,
                name = product.name,
                imageUrl = product.imageUrl,
                price = product.price,
            )
    }
}
