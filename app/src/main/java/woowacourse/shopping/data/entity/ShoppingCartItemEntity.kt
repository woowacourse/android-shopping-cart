package woowacourse.shopping.data.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_cart_item")
data class ShoppingCartItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @Embedded("product_")
    val product: ProductEntity,
    @ColumnInfo("totalQuantity")
    val totalQuantity: Int,
)
