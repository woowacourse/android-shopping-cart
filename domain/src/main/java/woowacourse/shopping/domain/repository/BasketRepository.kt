package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.Product

interface BasketRepository {
    fun add(product: Product)
    fun remove(product: Product)
    fun getAll(): List<Product>
}
