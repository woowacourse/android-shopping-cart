package woowacourse.shopping.view.cart.vm

import woowacourse.shopping.domain.product.Product

data class CartState(
    val cartId: Long,
    val product: Product,
)
