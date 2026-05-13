package woowacourse.shopping.domain.cart.model

import woowacourse.shopping.domain.product.model.Product

data class CartItem(
    val product: Product,
    val quantity: CartItemQuantity,
) {
    fun isSameCartItem(targetCartItem: CartItem): Boolean = product.id == targetCartItem.product.id

    fun increaseQuantity(): CartItem = copy(quantity = CartItemQuantity(quantity.value + 1))

    fun decreaseQuantity(): CartItem = copy(quantity = CartItemQuantity(quantity.value - 1))

    fun getCartItemTotalPrice(): Int = product.price.value * quantity.value
}
