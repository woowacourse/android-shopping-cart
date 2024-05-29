package woowacourse.shopping.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.shopping.data.db.cartItem.CartItemDatabase.Companion.CART_ITEMS_DB_NAME
import woowacourse.shopping.domain.model.Product

@Entity(tableName = CART_ITEMS_DB_NAME)
data class CartItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val product: Product,
    val productId: Long = product.id,
    val count: Int = product.cartItemCounter.itemCount,
) {
    companion object {
        const val DEFAULT_CART_ITEM_COUNT = 1
    }
}
