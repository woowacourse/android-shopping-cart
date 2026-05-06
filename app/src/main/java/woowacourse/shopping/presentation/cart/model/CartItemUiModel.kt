package woowacourse.shopping.presentation.cart.model

import woowacourse.shopping.presentation.common.model.ProductUiModel

data class CartItemUiModel(
    val product: ProductUiModel,
    val quantity: Int,
    val totalPrice: Long,
)
