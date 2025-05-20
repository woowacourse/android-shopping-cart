package woowacourse.shopping.data.shoppingCart.storage

import woowacourse.shopping.data.product.entity.ProductEntity

object VolatileShoppingCartStorage : ShoppingCartStorage {
    private var products: List<ProductEntity> = emptyList()

    override fun load(): List<ProductEntity> = products

    override fun add(product: ProductEntity) {
        products += product
    }

    override fun remove(product: ProductEntity) {
        products -= product
    }
}
