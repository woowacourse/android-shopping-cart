package woowacourse.shopping.data.datasource

import woowacourse.shopping.domain.model.Product

interface ProductDataSource {
    fun getProducts(): List<Product>

    fun getProductById(id: Long): Product?

    fun getPagedProducts(
        page: Int,
        pageSize: Int,
    ): List<Product>
}
