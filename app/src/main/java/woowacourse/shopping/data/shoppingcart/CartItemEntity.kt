package woowacourse.shopping.data.shoppingcart

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.shopping.data.shoppingcart.CartItemEntity.Companion.CART_ITEM_TABLE_NAME

@Entity(tableName = CART_ITEM_TABLE_NAME)
data class CartItemEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val price: Int,
    val quantity: Int,
    @ColumnInfo(name = "image_url") val imageUrl: String,
) {
    companion object {
        const val CART_ITEM_TABLE_NAME = "cart_item"
    }
}
