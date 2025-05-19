package woowacourse.shopping.data.mapper

import woowacourse.shopping.data.dto.CartProductDetailDto
import woowacourse.shopping.data.entity.CartProductEntity
import woowacourse.shopping.domain.model.CartProduct

fun CartProductDetailDto.toDomain(): CartProduct =
    CartProduct(
        product = product.toDomain(),
        quantity = cartProduct.quantity,
    )

fun CartProduct.toData(): CartProductEntity =
    CartProductEntity(
        productId = product.id,
        quantity = quantity,
    )
