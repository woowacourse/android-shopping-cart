package woowacourse.shopping.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
class Cart(
    val cartProducts: CartProducts,
) : Parcelable {
    fun size() = cartProducts.size()

    fun addProduct(product: Product): Cart {
        val product = cartProducts.add(product)
        return Cart(product)
    }

    fun removeProduct(id: UUID): Cart {
        val product = cartProducts.remove(id)
        return Cart(product)
    }
}
