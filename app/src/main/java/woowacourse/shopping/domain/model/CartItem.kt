package woowacourse.shopping.domain.model

import woowacourse.shopping.data.db.cart.CartRepository.Companion.DEFAULT_QUANTITY

data class CartItem(
    val id: Long,
    val productId: Long,
    val productName: String,
    val price: Long,
    val imageUrl: String,
    val quantity: Int,
) {
    fun plusQuantity(): CartItem {
        return copy(quantity = quantity + 1)
    }

    fun minusQuantity(): CartItem {
        return copy(quantity = (this.quantity - 1).coerceAtLeast(DEFAULT_QUANTITY))
    }

    fun totalPrice(): Long = price * quantity
}
