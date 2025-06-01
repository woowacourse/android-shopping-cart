package woowacourse.shopping.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.shopping.domain.product.CartItem

@Entity(tableName = "cart_item")
data class CartItemEntity(
    @PrimaryKey val id: Long,
    @ColumnInfo("quantity") val quantity: Int,
) {
    fun toDomain(productEntity: ProductEntity): CartItem = CartItem(
        id = id,
        product = productEntity.toDomain(),
        quantity = quantity
    )
}
