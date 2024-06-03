package woowacourse.shopping.data.db.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class OrderEntity(
    @PrimaryKey(autoGenerate = true) val orderId: Int = 0,
    val quantity: Int,
    @Embedded val product: ProductEntity,
)
