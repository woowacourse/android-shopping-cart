package woowacourse.shopping.data.shoppingcart

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_cart")
data class ShoppingCartItemEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val price: Int,
    val quantity: Int,
    val imageUrl: String,
)
