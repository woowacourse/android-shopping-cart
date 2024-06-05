package woowacourse.shopping.data.local.mapper

import woowacourse.shopping.data.local.entity.CartProductEntity
import woowacourse.shopping.domain.CartProduct

fun CartProductEntity.toDomain(): CartProduct {
    return CartProduct(
        productId,
        name,
        imgUrl,
        price,
        quantity ?: 0,
    )
}
