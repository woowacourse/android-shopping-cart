package woowacourse.shopping.ui.model

import woowacourse.shopping.model.CartItem

fun CartItem.toUiModel(): CartItemUiModel =
    CartItemUiModel(
        product = product.toUiModel(),
        quantity = quantity,
        totalPrice = getTotalPrice().amount,
    )
