package woowacourse.shopping.data.cart

import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.shopping.model.Quantity

@Entity(tableName = "carts")
data class Cart(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    val productId: Long,
    val quantity: Quantity = Quantity(),
) {
    operator fun inc(): Cart {
        return this.copy(quantity = quantity.inc())
    }

    operator fun dec(): Cart {
        return this.copy(quantity = quantity.dec())
    }
}
