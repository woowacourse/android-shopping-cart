package woowacourse.shopping.data.shoppingCart.storage

import woowacourse.shopping.data.product.entity.CartItemEntity

object VolatileShoppingCartStorage : ShoppingCartStorage {
    private var products: List<CartItemEntity> = emptyList()

    override fun load(): List<CartItemEntity> = products.toList()

    override fun add(product: CartItemEntity) {
        products += product
    }

    override fun remove(product: CartItemEntity) {
        products -= product
    }

    override fun update(products: List<CartItemEntity>) {
        this.products = products
    }
}
