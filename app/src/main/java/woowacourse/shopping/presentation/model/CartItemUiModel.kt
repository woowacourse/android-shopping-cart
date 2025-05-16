package woowacourse.shopping.presentation.model

import woowacourse.shopping.domain.model.CartItem

data class CartItemUiModel(
    val id: Long,
    val product: ProductUiModel,
    val quantity: Int,
) {
    val totalPrice: Int get() = product.price * quantity
}

fun CartItem.toUiModel(product: ProductUiModel) = CartItemUiModel(id, product, quantity)
