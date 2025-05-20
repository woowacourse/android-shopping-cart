package woowacourse.shopping.data.repository

import woowacourse.shopping.data.DummyShoppingCart
import woowacourse.shopping.domain.Product
import kotlin.math.min

class ShoppingCartRepositoryImpl : ShoppingCartRepository {
    override fun findAll(
        offset: Int,
        limit: Int,
    ): List<Product> {
        return DummyShoppingCart.products
            .distinct()
            .subList(offset, min(offset + limit, DummyShoppingCart.products.size))
    }

    override fun totalSize(): Int = DummyShoppingCart.products.size

    override fun remove(product: Product) {
        DummyShoppingCart.products.remove(product)
    }
}
