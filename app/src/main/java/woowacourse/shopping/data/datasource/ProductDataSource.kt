package woowacourse.shopping.data.datasource

import woowacourse.shopping.data.model.Product

interface ProductDataSource {
    fun getProducts(
        page: Int,
        pageSize: Int,
    ): List<Product>

    fun getProductById(id: Long): Product
}
