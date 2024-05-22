package woowacourse.shopping.data.cart

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
data class CartEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val productId: Long,
    val quantity: Int,
){

}
