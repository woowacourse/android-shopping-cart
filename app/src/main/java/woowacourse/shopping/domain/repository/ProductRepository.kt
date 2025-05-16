package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.Product

interface ProductRepository {
    operator fun get(id: Long): Product

    fun loadSinglePage(
        page: Int,
        pageSize: Int,
    ): List<Product>
}
