package woowacourse.shopping.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_cart_items")
data class ShoppingCartItemEntity(
    @PrimaryKey val productId: String,
)
