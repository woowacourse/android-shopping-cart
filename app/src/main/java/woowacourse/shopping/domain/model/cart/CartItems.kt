package woowacourse.shopping.domain.model.cart

import woowacourse.shopping.domain.model.Quantity

data class CartItems(
    private val _items: List<CartItem> = emptyList(),
) {
    val items: List<CartItem>
        get() = _items

    fun add(newItem: CartItem): CartItems {
        val existingItem = _items.find { it.product.id == newItem.product.id }

        val newItems = if (existingItem != null) {
            _items.map { cartItem ->
                if (cartItem.product.id != newItem.product.id) return@map cartItem
                cartItem.copy(quantity = Quantity(cartItem.quantity.value + newItem.quantity.value))
            }
        } else {
            _items + newItem
        }

        return CartItems(newItems)
    }

    fun remove(productId: String): CartItems {
        val newItems = _items.filterNot { it.product.id == productId }
        return CartItems(newItems)
    }
}

