package woowacourse.shopping.domain.cart

import woowacourse.shopping.domain.product.Money
import java.io.Serializable

data class CartProduct(
    val id: Long? = null,
    val productId: Long,
    val imageUrl: String,
    val name: String,
    private val _price: Money,
) : Serializable {
    val price: Int get() = _price.amount
}
