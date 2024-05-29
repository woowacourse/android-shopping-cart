package woowacourse.shopping.data.datasource.product

import woowacourse.shopping.data.model.Product

interface ProductDataSource {
    fun getProducts(
        page: Int,
        pageSize: Int,
    ): List<Product>

    fun getProductById(id: Long): Product

    fun getProductByIds(ids: List<Long>): List<Product>
}
