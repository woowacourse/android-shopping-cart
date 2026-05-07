package woowacourse.shopping.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
data class CartEntity(
    @PrimaryKey val productId: String,
    val quantity: Int,
)
