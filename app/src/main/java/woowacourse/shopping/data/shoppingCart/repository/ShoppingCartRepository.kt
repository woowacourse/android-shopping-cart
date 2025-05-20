package woowacourse.shopping.data.shoppingCart.repository

import woowacourse.shopping.domain.product.Product

interface ShoppingCartRepository {
    fun load(): List<Product>

    fun add(product: Product)

    fun remove(product: Product)
}
