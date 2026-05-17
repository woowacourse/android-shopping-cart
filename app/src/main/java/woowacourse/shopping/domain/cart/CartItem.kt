package woowacourse.shopping.domain.cart

import woowacourse.shopping.domain.product.Product

data class CartItem(
    val product: Product,
    val quantity: Quantity = Quantity.ONE,
) {
    val totalPrice: Int
        get() = product.price.value * quantity.value

    fun isSameCartItem(targetCartItem: CartItem): Boolean = product.id == targetCartItem.product.id

    fun isSameProduct(productId: Int): Boolean = product.id == productId

    fun increaseQuantity(): CartItem = copy(quantity = quantity.increase())

    fun decreaseQuantity(): CartItem = copy(quantity = quantity.decrease())
}
