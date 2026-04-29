package woowacourse.shopping.data.dto.request

import woowacourse.shopping.domain.model.cart.CartItem

data class CartItemRequestDto(
    val productId: String,
    val quantity: Int = 1
)

fun CartItem.toRequestDto(): CartItemRequestDto {
    return CartItemRequestDto(
        productId = this.product.id,
        quantity = this.quantity.value
    )
}
