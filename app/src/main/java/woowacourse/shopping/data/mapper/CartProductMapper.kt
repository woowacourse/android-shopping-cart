package woowacourse.shopping.data.mapper

import woowacourse.shopping.data.cart.CartProductEntity
import woowacourse.shopping.domain.CartProduct

fun CartProductEntity.toDomain() =
    CartProduct(
        id = this.id,
        product = this.productId.toProduct(),
        quantity = this.quantity,
    )

fun CartProduct.toEntity() =
    CartProductEntity(
        productId = this.product.toId(),
        quantity = this.quantity,
    )
