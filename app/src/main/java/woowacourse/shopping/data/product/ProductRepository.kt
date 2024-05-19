package woowacourse.shopping.data.product

import woowacourse.shopping.model.Product

interface ProductRepository {
    fun find(id: Long): Product

    fun findRange(
        page: Int,
        pageSize: Int,
    ): List<Product>
}
