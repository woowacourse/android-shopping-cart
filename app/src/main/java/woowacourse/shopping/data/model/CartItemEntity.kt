package woowacourse.shopping.data.model

import androidx.room.Entity
import woowacourse.shopping.data.db.CartItemDatabase.Companion.CART_ITEMS_DB_NAME
import woowacourse.shopping.domain.Product

@Entity(tableName = CART_ITEMS_DB_NAME)
data class CartItemEntity(val id: Long, val product: Product)
