package woowacourse.shopping.domain.cart

import java.io.Serializable
import woowacourse.shopping.domain.product.Product

data class CartProduct(
    val id: Long? = null,
    val product: Product,
    private var _quantity: Int,
) : Serializable {
    val quantity get() = _quantity
    fun increase() {
        _quantity++
    }

    fun decrease() {
        _quantity--
    }

    fun totalPrice(): Int {
        return product.price * quantity
    }
}
