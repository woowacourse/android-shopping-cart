package woowacourse.shopping.data.repository

import woowacourse.shopping.domain.Product

interface ShoppingCartRepository {
    fun findAll(): List<Product>

    fun remove(product: Product)
}
