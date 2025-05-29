package woowacourse.shopping.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_cart")
data class ShoppingCartEntity(
    @PrimaryKey val productId: Long = 0,
    @ColumnInfo(name = "quantity") val quantity: Int,
)
