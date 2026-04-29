package woowacourse.shopping.core.uimodel

import woowacourse.shopping.feature.cart.model.CartItem

fun CartItem.toUiModel(): CartItemUiModel =
    CartItemUiModel(
        product = product.toUiModel(),
        quantity = quantity,
        totalPrice = getTotalPrice().amount,
    )
