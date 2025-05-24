package woowacourse.shopping.data.datasource

import woowacourse.shopping.domain.model.Product

interface ProductDataSource {
    fun start()

    fun fetchProducts(): List<Product>

    fun fetchProductById(id: Long): Product

    fun fetchPagingProducts(
        page: Int,
        pageSize: Int,
    ): List<Product>

    fun shutdown()
}
