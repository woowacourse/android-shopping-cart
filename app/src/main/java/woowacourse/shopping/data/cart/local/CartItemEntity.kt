package woowacourse.shopping.data.cart.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.shopping.model.CartItem
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.Quantity

@Entity(tableName = "cart")
data class CartItemEntity(
    @ColumnInfo(name = "product") val product: Product,
    @ColumnInfo(name = "quantity") val quantity: Quantity,
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Long = 0L

    companion object {
        fun CartItemEntity.toDomainModel() =
            CartItem(
                product = product,
                quantity = quantity,
            )
    }
}
