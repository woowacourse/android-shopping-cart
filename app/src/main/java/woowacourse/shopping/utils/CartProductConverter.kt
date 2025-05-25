package woowacourse.shopping.utils

import woowacourse.shopping.data.cart.CartItemDetail
import woowacourse.shopping.domain.cart.CartProduct

fun CartItemDetail.toCartProduct(): CartProduct {
    return CartProduct(
        this.cartItemEntity.id,
        this.productEntity.toProduct(),
        this.cartItemEntity.quantity
    )
}
