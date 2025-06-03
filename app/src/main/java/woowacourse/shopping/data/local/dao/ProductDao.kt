package woowacourse.shopping.data.local.dao

import woowacourse.shopping.domain.product.Product

interface ProductDao {
    fun findById(id: Long): Product?

    fun findPagedItems(
        limit: Int,
        offset: Int,
    ): List<Product>

    fun size(): Int
}
