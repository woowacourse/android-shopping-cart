package woowacourse.shopping.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Cart(
    @PrimaryKey
    val productId: Long,
    val quantity: Int
)