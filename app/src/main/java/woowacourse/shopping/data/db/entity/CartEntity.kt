package woowacourse.shopping.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.shopping.domain.Quantity
import woowacourse.shopping.domain.cart.Cart

@Entity(tableName = "cart_table")
data class CartEntity(
    @PrimaryKey
    val productId: Long,
    val quantity: Int,
) {
    fun toDomain(): Cart {
        return Cart(
            productId = productId,
            quantity = Quantity(quantity),
        )
    }
}
