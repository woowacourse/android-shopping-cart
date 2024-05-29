package woowacourse.shopping.data.product.remote

import woowacourse.shopping.model.Product

interface ProductService {
    fun find(id: Long): Product

    fun findAll(): List<Product>

    fun findRange(
        page: Int,
        pageSize: Int,
    ): List<Product>

    fun shutdown()
}
