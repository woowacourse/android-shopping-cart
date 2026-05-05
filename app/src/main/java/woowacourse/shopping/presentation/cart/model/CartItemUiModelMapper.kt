package woowacourse.shopping.presentation.cart.model

import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.presentation.common.model.toUiModel

fun CartItem.toUiModel(): CartItemUiModel =
    CartItemUiModel(
        product = product.toUiModel(),
        quantity = quantity,
        totalPrice = getTotalPrice().amount,
    )
