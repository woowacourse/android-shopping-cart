package woowacourse.shopping.data.repository

import woowacourse.shopping.domain.Product

interface ProductsRepository {
    fun findAll(
        offset: Int,
        limit: Int,
    ): List<Product>

    fun totalSize(): Int
}
