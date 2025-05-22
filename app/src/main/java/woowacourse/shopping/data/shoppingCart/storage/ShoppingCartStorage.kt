package woowacourse.shopping.data.shoppingCart.storage

import woowacourse.shopping.data.product.entity.ProductEntity
import woowacourse.shopping.data.shoppingCart.entity.ShoppingCartProductEntity

interface ShoppingCartStorage {
    val size: Int

    fun load(
        offset: Int,
        limit: Int,
    ): List<ProductEntity>

    fun add(product: ProductEntity)

    fun addWithQuantity(shoppingCartProductEntity: ShoppingCartProductEntity)

    fun remove(product: ProductEntity)
}
