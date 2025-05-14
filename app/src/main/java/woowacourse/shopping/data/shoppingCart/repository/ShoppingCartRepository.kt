package woowacourse.shopping.data.shoppingCart.repository

import woowacourse.shopping.domain.product.Product

interface ShoppingCartRepository {
    val products: List<Product>

    fun add(product: Product)

    fun remove(product: Product)
}
