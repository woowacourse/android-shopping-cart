package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.Product

interface ProductRepository {
    fun load(
        startPage: Int,
        pageSize: Int,
    ): Result<List<Product>>

    fun loadById(id: Long): Result<Product>
}
