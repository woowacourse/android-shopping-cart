package woowacourse.shopping.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "cart_item",
    foreignKeys = [
        ForeignKey(
            entity = Product::class,
            parentColumns = ["id"],
            childColumns = ["productId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
)
data class CartItem(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val productId: Long,
    val quantity: Int = 1,
)
