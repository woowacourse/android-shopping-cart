package woowacourse.shopping.view.cart

import woowacourse.shopping.model.products.Product
import woowacourse.shopping.model.products.ShoppingCartItem

class CartRepositoryImpl : CartRepository {
    private var cartItems = mutableMapOf<String, ShoppingCartItem>()

    override fun add(product: Product) {
        val existingItem = cartItems[product.id]
        if (existingItem != null) {
            val updatedItem = existingItem.copy(quantity = existingItem.quantity + 1)
            cartItems[product.id] = updatedItem
        } else {
            val newItem = ShoppingCartItem(product, 1)
            cartItems[product.id] = newItem
        }
    }

    override fun updateQuantity(
        productId: String,
        quantity: Int,
    ) {
        if (quantity <= 0) {
            cartItems.remove(productId)
        } else {
            val existingItem = cartItems[productId]
            if (existingItem != null) {
                val updatedItem = existingItem.copy(quantity = quantity)
                cartItems[productId] = updatedItem
            }
        }
    }

    override fun getAllShoppingCartItem(): List<ShoppingCartItem> = cartItems.values.toList()

    override fun remove(product: Product) {
        cartItems.remove(product.id)
    }

    override fun clear() {
        cartItems.clear()
    }
}
