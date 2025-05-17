package woowacourse.shopping.data.mapper

import woowacourse.shopping.data.cart.ShoppingCartEntity
import woowacourse.shopping.domain.CartProduct

fun List<ShoppingCartEntity>.toDomain() =
    this.map { entity ->
        CartProduct(
            id = entity.id,
            product = entity.productId.toProduct(),
        )
    }

fun CartProduct.toEntity() =
    ShoppingCartEntity(
        id = this.id,
        productId = this.product.toId(),
    )
