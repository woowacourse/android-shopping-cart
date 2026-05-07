package woowacourse.shopping.domain.cart.model

import woowacourse.shopping.domain.product.model.Product

data class CartItem(
    val product: Product,
    val quantity: CartItemQuantity,
) {
    fun isSameCartItem(targetCartItem: CartItem): Boolean = product.id == targetCartItem.product.id

    fun increaseQuantity(targetQuantity: Int): CartItem = copy(quantity = CartItemQuantity(quantity.value + targetQuantity))

    fun decreaseQuantity(targetQuantity: Int): CartItem = copy(quantity = CartItemQuantity(quantity.value - targetQuantity))

    fun getCartItemTotalPrice(): Int = product.price.value * quantity.value
}
