package woowacourse.shopping.data.datasource.remote

import woowacourse.shopping.domain.model.Product

interface ProductDataSource {
    fun fetchPagingProducts(
        page: Int,
        pageSize: Int,
    ): List<Product>

    fun fetchProductById(id: Long): Product
}
