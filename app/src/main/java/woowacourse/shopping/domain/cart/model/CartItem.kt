package woowacourse.shopping.domain.cart.model

import woowacourse.shopping.domain.product.model.Product
import java.util.UUID

data class CartItem(
    val id: String = UUID.randomUUID().toString(),
    val product: Product,
) {
    fun isSameCartItem(targetCartItem: CartItem): Boolean = id == targetCartItem.id
}
