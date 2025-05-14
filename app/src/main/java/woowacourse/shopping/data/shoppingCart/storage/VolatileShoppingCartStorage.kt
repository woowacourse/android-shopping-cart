package woowacourse.shopping.data.shoppingCart.storage

import woowacourse.shopping.data.product.entity.ProductEntity

object VolatileShoppingCartStorage : ShoppingCartStorage {
    override var products: List<ProductEntity> = emptyList()
        private set

    override fun add(product: ProductEntity) {
        products += product
    }

    override fun remove(product: ProductEntity) {
        products -= product
    }
}
