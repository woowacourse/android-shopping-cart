package woowacourse.shopping.data.repository

import woowacourse.shopping.data.DummyShoppingCart
import woowacourse.shopping.domain.Product

class ShoppingCartRepositoryImpl : ShoppingCartRepository {
    override fun findAll(): List<Product> {
        return DummyShoppingCart.products.distinct()
    }

    override fun remove(product: Product) {
        DummyShoppingCart.products.remove(product)
    }
}
