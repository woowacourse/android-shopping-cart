package woowacourse.shopping.data.mapper

import woowacourse.shopping.data.shoppingcart.ShoppingCartEntity
import woowacourse.shopping.domain.ShoppingProduct

fun List<ShoppingCartEntity>.toDomain() =
    this.map { entity ->
        ShoppingProduct(
            productId = entity.productId,
            quantity = entity.quantity,
        )
    }

fun ShoppingCartEntity.toDomain() =
    ShoppingProduct(
        productId = this.productId,
        quantity = this.quantity,
    )

fun ShoppingProduct.toEntity() =
    ShoppingCartEntity(
        productId = this.productId,
        quantity = this.quantity ?: 0,
    )
