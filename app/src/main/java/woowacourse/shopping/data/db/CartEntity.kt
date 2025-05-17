package woowacourse.shopping.data.db

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [Index(value = ["productId"], unique = true)],
)
data class CartEntity(
    @PrimaryKey(autoGenerate = true) val cartId: Long = 0,
    val productId: Long,
    val quantity: Int = 1,
    val createdAt: Long = System.currentTimeMillis(),
)
