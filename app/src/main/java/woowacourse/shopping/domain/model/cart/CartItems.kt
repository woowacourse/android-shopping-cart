package woowacourse.shopping.domain.model.cart

import woowacourse.shopping.domain.model.Quantity

class CartItems(
    items: List<CartItem> = emptyList(),
) {
    val items: List<CartItem> = items.toList()
    fun addOrMerge(newItem: CartItem): CartItems {
        val existingItem = items.find { it.product.id == newItem.product.id }

        val newItems =
            if (existingItem != null) {
                items.map { cartItem ->
                    if (cartItem.product.id != newItem.product.id) return@map cartItem
                    cartItem.copy(quantity = Quantity(cartItem.quantity.value + newItem.quantity.value))
                }
            } else {
                items + newItem
            }

        return CartItems(newItems)
    }

    fun remove(productId: String): CartItems {
        val newItems = items.filterNot { it.product.id == productId }
        return CartItems(newItems)
    }
}
