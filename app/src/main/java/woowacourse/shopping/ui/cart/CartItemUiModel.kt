package woowacourse.shopping.ui.cart

import woowacourse.shopping.model.ProductId

data class CartItemUiModel(
    val productId: ProductId,
    val name: String,
    val imageUrl: String,
    val price: Int,
    val quantity: Int,
)
