package woowacourse.shopping

import woowacourse.shopping.domain.Product

interface ProductRepository {
    fun products(): List<Product>

    fun products(
        startPosition: Int,
        offset: Int,
    ): List<Product>

    fun productById(id: Long): Product

    fun productsTotalSize(): Int
}
