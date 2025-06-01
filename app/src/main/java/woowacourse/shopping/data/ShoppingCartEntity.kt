package woowacourse.shopping.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_cart")
data class ShoppingCartEntity(
    @PrimaryKey val productId: Int,
    val quantity: Int,
)
