package woowacourse.shopping.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CartProducts")
data class CartProductEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    val imageUrl: String,
    val name: String,
    val price: Int,
    val quantity: Int,
)
