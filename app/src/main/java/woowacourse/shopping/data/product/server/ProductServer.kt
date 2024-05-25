package woowacourse.shopping.data.product.server

import woowacourse.shopping.data.product.entity.Product

interface ProductServer {
    fun start()

    fun findOrNull(id: Long): Product?

    fun findRange(
        page: Int,
        pageSize: Int,
    ): List<Product>

    fun totalCount(): Int

    fun shutDown()
}
