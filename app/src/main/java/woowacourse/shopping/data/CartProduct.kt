package woowacourse.shopping.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cartProducts")
data class CartProduct(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    val imageUrl: Int,
    val name: String,
    val price: Int,
)
