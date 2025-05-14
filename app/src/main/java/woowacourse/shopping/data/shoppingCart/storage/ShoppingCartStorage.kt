package woowacourse.shopping.data.shoppingCart.storage

import woowacourse.shopping.data.product.entity.ProductEntity

interface ShoppingCartStorage {
    val products: List<ProductEntity>

    fun add(product: ProductEntity)

    fun remove(product: ProductEntity)
}
