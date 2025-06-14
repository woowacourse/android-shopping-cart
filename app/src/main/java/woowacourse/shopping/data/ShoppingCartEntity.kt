package woowacourse.shopping.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.shopping.model.products.ShoppingCart

@Entity(tableName = "shopping_cart")
data class ShoppingCartEntity(
    @PrimaryKey val productId: Int,
    val quantity: Int,
) {
    fun toDomain(): ShoppingCart =
        ShoppingCart(
            productId = productId,
            quantity = quantity,
        )
}
