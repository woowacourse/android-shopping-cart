package woowacourse.shopping.data.db.cart

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.Product

@Entity(tableName = "cartProducts", indices = [Index(value = ["product"], unique = true)])
data class CartEntity(
    @PrimaryKey(autoGenerate = true) val uid: Long,
    @ColumnInfo(name = "product_id") val productId: Long,
    @ColumnInfo(name = "product") val product: Product,
    @ColumnInfo(name = "quantity") val quantity: Int,
) {
    fun toDomain() =
        Cart(
            product = product,
            quantity = quantity,
        )

    companion object {
        fun Cart.toEntity() =
            CartEntity(
                uid = 0,
                productId = this.product.id,
                product = this.product,
                quantity = this.quantity,
            )
    }
}
