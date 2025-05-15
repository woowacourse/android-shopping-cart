package woowacourse.shopping.data.shoppingCart.repository

import woowacourse.shopping.domain.product.Product

interface ShoppingCartRepository {
    fun load(page: Int, count: Int): List<Product>

    fun add(product: Product)

    fun remove(product: Product)
}
