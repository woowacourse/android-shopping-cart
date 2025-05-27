package woowacourse.shopping.domain.cart

import woowacourse.shopping.domain.product.Product
import java.io.Serializable

data class CartProduct(
    val id: Long? = null,
    val product: Product,
    private var _quantity: Int,
) : Serializable {
    val quantity get() = _quantity

    fun increase(): CartProduct {
        return copy(_quantity = quantity + 1)
    }

    fun decrease(): CartProduct {
        return copy(_quantity = quantity - 1)
    }

    fun totalPrice(): Int {
        return product.price * quantity
    }
}
