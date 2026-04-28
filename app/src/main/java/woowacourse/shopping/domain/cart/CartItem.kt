package woowacourse.shopping.domain.cart

import woowacourse.shopping.domain.product.Product
import java.util.UUID

data class CartItem(
    val id: String = UUID.randomUUID().toString(),
    val product: Product,
) {
    fun isSameCartItem(targetCartItem: CartItem): Boolean = id == targetCartItem.id
}
