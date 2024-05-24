package woowacourse.shopping.data.cart.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.shopping.model.Quantity

@Entity(tableName = "cart")
data class CartItem(
    @PrimaryKey(autoGenerate = true) var id: Long = 0L,
    @ColumnInfo(name = "product_id") val productId: Long,
    @ColumnInfo(name = "quantity") var quantity: Quantity,
)
