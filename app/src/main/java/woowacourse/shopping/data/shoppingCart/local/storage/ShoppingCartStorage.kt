package woowacourse.shopping.data.shoppingCart.local.storage

import woowacourse.shopping.data.product.local.entity.ProductEntity
import woowacourse.shopping.data.shoppingCart.local.entity.ShoppingCartProductEntity

interface ShoppingCartStorage {
    val quantity: Int

    val size: Int

    fun load(
        offset: Int,
        limit: Int,
    ): List<ShoppingCartProductEntity>

    fun add(shoppingCartProductEntity: ShoppingCartProductEntity)

    fun decreaseQuantity(product: ProductEntity)

    fun remove(product: ProductEntity)

    fun fetchQuantity(product: ProductEntity): Int
}
