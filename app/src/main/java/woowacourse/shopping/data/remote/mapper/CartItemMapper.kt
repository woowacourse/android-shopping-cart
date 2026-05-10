package woowacourse.shopping.data.remote.mapper

import woowacourse.shopping.data.remote.dto.CartItemResponse
import woowacourse.shopping.domain.model.cart.CartItem

fun CartItemResponse.toDomain(): CartItem =
    CartItem(
        product = product.toDomain(),
        quantity = quantity,
    )
