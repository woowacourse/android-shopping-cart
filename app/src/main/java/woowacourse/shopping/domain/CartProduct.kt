package woowacourse.shopping.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CartProduct(
    val productId: Long,
    val name: String,
    val imgUrl: String,
    val price: Long,
    var quantity: Int,
) : Parcelable {
    fun plusQuantity() {
        quantity = quantity.plus(1)
    }

    fun minusQuantity() {
        if (quantity > 0) quantity = quantity.minus(1)
    }
}
