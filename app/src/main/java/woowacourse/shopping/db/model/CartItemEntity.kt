package woowacourse.shopping.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CartItemEntity(
    @PrimaryKey val productId: Int,
    @ColumnInfo(name = "quantity") var quantity: Int
)
