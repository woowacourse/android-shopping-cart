package woowacourse.shopping.data.mapper

import woowacourse.shopping.data.shoppingcart.ShoppingCartEntity
import woowacourse.shopping.domain.ShoppingProduct

fun List<ShoppingCartEntity>.toDomain() =
    this.map { entity ->
        ShoppingProduct(
            id = entity.id,
            productId = entity.productId,
        )
    }

fun ShoppingProduct.toEntity() =
    ShoppingCartEntity(
        id = this.id,
        productId = this.productId,
    )
