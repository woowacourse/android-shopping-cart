package woowacourse.shopping.presentation.model

import woowacourse.shopping.domain.model.CartItem

data class CartItemUiModel(
    val product: ProductUiModel,
    val quantity: Int,
) {
    val totalPrice: Int get() = product.price * quantity
}

fun CartItem.toCartItemUiModel() = CartItemUiModel(product.toCartItemUiModel(), quantity)
