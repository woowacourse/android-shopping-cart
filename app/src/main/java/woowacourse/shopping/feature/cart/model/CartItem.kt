package woowacourse.shopping.feature.cart.model

import woowacourse.shopping.core.model.Money
import woowacourse.shopping.core.model.Product

data class CartItem(
    val product: Product,
    val quantity: Int,
) {
    init {
        require(quantity > 0) { "수량은 1개 이상이어야 합니다." }
        require(quantity < 100) { "수량은 100개 미만이어야 합니다." }
    }

    fun getTotalPrice(): Money = product.price * quantity
}
