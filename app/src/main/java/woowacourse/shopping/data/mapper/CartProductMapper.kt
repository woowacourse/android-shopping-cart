package woowacourse.shopping.data.mapper

import woowacourse.shopping.data.cart.CartProductEntity
import woowacourse.shopping.domain.CartProduct

fun List<CartProductEntity>.toDomain() =
    this.map { entity ->
        CartProduct(
            id = entity.id,
            product = entity.productId.toProduct(),
        )
    }

fun CartProduct.toEntity() =
    CartProductEntity(
        id = this.id,
        productId = this.product.toId(),
    )
