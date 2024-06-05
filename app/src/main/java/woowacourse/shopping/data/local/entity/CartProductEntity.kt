package woowacourse.shopping.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CartProductEntity(
    @PrimaryKey
    val productId: Long,
    val name: String,
    val imgUrl: String,
    val price: Long,
    val quantity: Int?,
)
