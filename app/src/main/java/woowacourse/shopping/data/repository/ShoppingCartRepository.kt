package woowacourse.shopping.data.repository

import woowacourse.shopping.domain.Product

interface ShoppingCartRepository {
    fun findAll(
        offset: Int,
        limit: Int,
    ): List<Product>

    fun totalSize(): Int

    fun remove(product: Product)
}
