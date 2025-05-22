package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.Product

interface ProductRepository {
    fun getById(id: Long): Product

    fun getProducts(
        page: Int,
        pageSize: Int,
    ): List<Product>

    fun notHasMoreProduct(
        page: Int,
        pageSize: Int,
    ): Boolean
}
