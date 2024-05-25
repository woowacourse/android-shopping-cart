package woowacourse.shopping.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

data class CartProduct(
    val productId: Long,
    val name: String,
    val imgUrl: String,
    val price: Long,
    var quantity: Int?
) {

    fun plusQuantity() {
        quantity = if(quantity == null) 1 else quantity?.plus(1)
    }

    fun minusQuantity() {
        quantity = if (quantity == 1) null else quantity?.minus(1)
    }
}
