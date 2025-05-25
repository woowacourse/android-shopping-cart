package woowacourse.shopping.data.shoppingCart.storage

import woowacourse.shopping.data.product.entity.CartItemEntity

object VolatileShoppingCartDataSource : ShoppingCartDataSource {
    private var cartItems: List<CartItemEntity> = emptyList()

    override fun load(): List<CartItemEntity> = cartItems.toList()

    override fun upsert(cartItem: CartItemEntity) {
        cartItems = cartItems.filterNot { it.id == cartItem.id }
        cartItems += cartItem
    }

    override fun remove(cartItem: CartItemEntity) {
        cartItems -= cartItem
    }

    override fun update(cartItems: List<CartItemEntity>) {
        this.cartItems = cartItems
    }

    override fun quantityOf(productId: Long): Int = cartItems.find { it.id == productId }?.quantity ?: 0
}
