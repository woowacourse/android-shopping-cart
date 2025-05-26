package woowacourse.shopping.data.mapper

import woowacourse.shopping.data.model.response.CartProductDetailResponse
import woowacourse.shopping.domain.model.CartProduct
import woowacourse.shopping.domain.model.Product.Companion.EMPTY_PRODUCT

fun CartProductDetailResponse.toDomain(): CartProduct =
    CartProduct(
        product = product?.toDomain() ?: EMPTY_PRODUCT,
        quantity = cartProduct.quantity,
    )
