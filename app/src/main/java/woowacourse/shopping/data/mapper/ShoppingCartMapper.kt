package woowacourse.shopping.data.mapper

import woowacourse.shopping.data.ShoppingCartEntity
import woowacourse.shopping.domain.ShoppingProduct

fun List<ShoppingCartEntity>.toDomain() =
    this.map { entity ->
        ShoppingProduct(
            position = entity.id,
            productId = entity.productId,
        )
    }

fun ShoppingProduct.toEntity() =
    ShoppingCartEntity(
        id = this.position,
        productId = this.productId,
    )
