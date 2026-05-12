package woowacourse.shopping.data.source.local.cart

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
data class CartEntity(
    @PrimaryKey val productId: Long,
    val quantity: Int,
)
