package woowacourse.shopping.data.shoppingCart.repository

import woowacourse.shopping.data.product.entity.ProductEntity
import woowacourse.shopping.data.product.entity.ProductEntity.Companion.toEntity
import woowacourse.shopping.data.shoppingCart.storage.ShoppingCartStorage
import woowacourse.shopping.data.shoppingCart.storage.VolatileShoppingCartStorage
import woowacourse.shopping.domain.product.Product

class DefaultShoppingCartRepository(
    private val shoppingCartStorage: ShoppingCartStorage = VolatileShoppingCartStorage,
) : ShoppingCartRepository {
    override fun load(): List<Product> = shoppingCartStorage.load().map(ProductEntity::toDomain)

    override fun add(product: Product) {
        shoppingCartStorage.add(product.toEntity())
    }

    override fun remove(product: Product) {
        shoppingCartStorage.remove(product.toEntity())
    }
}
