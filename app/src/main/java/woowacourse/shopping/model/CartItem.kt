package woowacourse.shopping.model

import woowacourse.shopping.data.cart.local.CartItemEntity

data class CartItem(
    val product: Product,
    val quantity: Quantity,
) {
    companion object {
        fun CartItem.toEntity() =
            CartItemEntity(
                product = product,
                quantity = quantity,
            )
    }
}
