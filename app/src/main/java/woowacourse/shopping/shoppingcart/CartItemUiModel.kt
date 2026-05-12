package woowacourse.shopping.shoppingcart

import woowacourse.shopping.ui.DisplayText

data class CartItemUiModel(
    val productId: String,
    val title: String,
    val imageUrl: String,
    val price: DisplayText,
    val quantity: Int,
)
