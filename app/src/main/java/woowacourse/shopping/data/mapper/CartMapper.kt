package woowacourse.shopping.data.mapper

import woowacourse.shopping.data.dto.CartProductDetailDto
import woowacourse.shopping.data.entity.CartProductEntity
import woowacourse.shopping.domain.model.CartProduct
import woowacourse.shopping.domain.model.Product.Companion.EMPTY_PRODUCT

fun CartProductDetailDto.toDomain(): CartProduct =
    CartProduct(
        product = product?.toDomain() ?: EMPTY_PRODUCT,
        quantity = cartProduct.quantity,
    )

fun CartProduct.toData(): CartProductEntity =
    CartProductEntity(
        productId = product.id,
        quantity = quantity,
    )
