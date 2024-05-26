package woowacourse.shopping.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_cart_products")
data class ProductIdsCountData(
    @PrimaryKey(autoGenerate = false)
    val productId: Long = -1,
    val quantity: Int,
)
