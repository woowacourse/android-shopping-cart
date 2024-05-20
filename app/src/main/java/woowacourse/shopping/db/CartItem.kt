package woowacourse.shopping.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CartItem(
    @PrimaryKey val productId: Int,
    @ColumnInfo(name = "quantity") var quantity: Int
)
