package woowacourse.shopping.ui.model.mapper

import woowacourse.shopping.model.CartItem
import woowacourse.shopping.ui.model.CartItemUiModel

fun CartItem.toUiModel(): CartItemUiModel =
    CartItemUiModel(
        product = product.toUiModel(),
        quantity = quantity,
        totalPrice = getTotalPrice().amount,
    )
