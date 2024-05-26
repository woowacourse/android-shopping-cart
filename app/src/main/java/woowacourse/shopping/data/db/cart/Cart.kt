package woowacourse.shopping.data.db.cart

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "carts")
data class Cart(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "quantity")
    val quantity: Int,
    @ColumnInfo(name = "productId")
    val productId: Long,
)
