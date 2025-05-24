package woowacourse.shopping.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_products")
data class CartProductEntity(
    @PrimaryKey val productId: Int,
    val quantity: Int,
)
