package woowacourse.shopping.data.cart

import woowacourse.shopping.domain.model.CartProduct

fun CartProduct.toEntity() =
    CartProductEntity(
        productId = this.product.id,
        quantity = this.quantity,
    )
