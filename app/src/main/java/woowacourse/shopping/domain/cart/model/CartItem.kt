package woowacourse.shopping.domain.cart.model

import woowacourse.shopping.domain.product.model.Product

data class CartItem(
    val product: Product,
    val quantity: CartItemQuantity,
) {
    fun isSameCartItem(targetCartItem: CartItem): Boolean = product.id == targetCartItem.product.id

    fun increaseQuantity(targetCartItem: CartItem): CartItem = copy(quantity = CartItemQuantity(quantity.value + targetCartItem.quantity.value))

    fun decreaseQuantity(targetCartItem: CartItem): CartItem = copy(quantity = CartItemQuantity(quantity.value - targetCartItem.quantity.value))

    fun getTotalPrice(): Int = product.price.value * quantity.value
}
