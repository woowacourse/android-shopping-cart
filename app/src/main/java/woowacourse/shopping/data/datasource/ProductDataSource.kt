package woowacourse.shopping.data.datasource

import woowacourse.shopping.domain.Product

interface ProductDataSource {
    operator fun get(id: Long): Product

    fun getProducts(
        page: Int,
        pageSize: Int,
    ): List<Product>

    fun notHasMoreProduct(
        page: Int,
        pageSize: Int,
    ): Boolean
}
