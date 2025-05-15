package woowacourse.shopping.data.shoppingCart.storage

import woowacourse.shopping.data.product.entity.ProductEntity

interface ShoppingCartStorage {
    val size: Int

    fun load(start: Int, endExclusive: Int): List<ProductEntity>

    fun add(product: ProductEntity)

    fun remove(product: ProductEntity)
}
