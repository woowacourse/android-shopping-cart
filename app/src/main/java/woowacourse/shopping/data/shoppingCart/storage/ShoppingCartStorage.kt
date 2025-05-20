package woowacourse.shopping.data.shoppingCart.storage

import woowacourse.shopping.data.product.entity.ProductEntity

interface ShoppingCartStorage {
    fun load(): List<ProductEntity>

    fun add(product: ProductEntity)

    fun remove(product: ProductEntity)
}
