package woowacourse.shopping.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
class Cart(
    val cartProducts: CartProducts,
) : Parcelable {
    fun getUniqueItemCount() = cartProducts.uniqueItemCount
    fun getTotalQuantity() = cartProducts.totalQuantity

    fun addProduct(product: Product, plusAmount: Int = 1): Cart {
        val newCartProducts = cartProducts.addQuantityOfCartProduct(product, plusAmount)
        return Cart(newCartProducts)
    }

    fun decreaseProduct(id: UUID, minusAmount: Int = 1): Cart {
        val newCartProducts = cartProducts.decreaseQuantityOfCartProduct(id, minusAmount)
        return Cart(newCartProducts)
    }

    fun removeProduct(id: UUID): Cart {
        val product = cartProducts.remove(id)
        return Cart(product)
    }

    fun calculateTotalPrice(): Long = cartProducts.calculateTotalPrice()

}
