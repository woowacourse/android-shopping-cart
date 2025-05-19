package woowacourse.shopping.data.shoppingCart.storage

import woowacourse.shopping.data.product.entity.ProductEntity

object VolatileShoppingCartStorage : ShoppingCartStorage {
    private var products: List<ProductEntity> = emptyList()
    override val size: Int
        get() {
            return products.size
        }

    override fun load(
        offset: Int,
        limit: Int,
    ): List<ProductEntity> {
        if (limit > size) return products.subList(offset, size)
        return products.subList(offset, limit)
    }

    override fun add(product: ProductEntity) {
        products += product
    }

    override fun remove(product: ProductEntity) {
        products -= product
    }
}
