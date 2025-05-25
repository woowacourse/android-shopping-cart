package woowacourse.shopping.data.shoppingCart.storage

import woowacourse.shopping.data.product.entity.CartItemEntity

object VolatileShoppingCartStorage : ShoppingCartStorage {
    private var cartItems: List<CartItemEntity> = emptyList()

    override fun load(): List<CartItemEntity> = cartItems.toList()

    override fun add(product: CartItemEntity) {
        cartItems += product
    }

    override fun remove(product: CartItemEntity) {
        cartItems -= product
    }

    override fun update(products: List<CartItemEntity>) {
        this.cartItems = products
    }

    override fun quantityOf(productId: Long): Int = cartItems.find { it.id == productId }?.quantity ?: 0
}
