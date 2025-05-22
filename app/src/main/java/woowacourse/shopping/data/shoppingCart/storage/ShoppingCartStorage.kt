package woowacourse.shopping.data.shoppingCart.storage

import woowacourse.shopping.data.product.entity.CartItemEntity

interface ShoppingCartStorage {
    fun load(): List<CartItemEntity>

    fun add(product: CartItemEntity)

    fun remove(product: CartItemEntity)

    fun update(products: List<CartItemEntity>)
}
