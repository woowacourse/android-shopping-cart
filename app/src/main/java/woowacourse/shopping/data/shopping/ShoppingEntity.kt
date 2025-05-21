package woowacourse.shopping.data.shopping

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shoppingCart")
data class ShoppingEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo val quantity: Int,
)
