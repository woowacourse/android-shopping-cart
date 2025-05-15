package woowacourse.shopping.data.shoppingCart.storage

import woowacourse.shopping.data.product.entity.ProductEntity

object VolatileShoppingCartStorage : ShoppingCartStorage {
    private var products: List<ProductEntity> = emptyList()
    override val size: Int
        get() {
            return products.size
        }

    override fun load(
        start: Int,
        endExclusive: Int
    ): List<ProductEntity> {
        if (endExclusive > size) return products.subList(start, size)
        return products.subList(start, endExclusive)
    }

    override fun add(product: ProductEntity) {
        products += product
    }

    override fun remove(product: ProductEntity) {
        products -= product
    }
}
