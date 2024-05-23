package woowacourse.shopping.domain.model

data class CartItem(val id: Long, val product: Product, val quantity: Int = 1) {
    val price: Int
        get() = product.price * quantity
}

fun CartItem.incrementQuantity(incrementBy: Int): CartItem {
    return this.copy(quantity = this.quantity + incrementBy)
}

fun CartItem.decrementQuantity(decrementBy: Int): CartItem {
    return this.copy(quantity = this.quantity - decrementBy)
}
