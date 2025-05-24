package woowacourse.shopping.data.remote

import woowacourse.shopping.domain.model.Product

interface ProductService {
    fun start()

    fun fetchProductById(id: Long): Product

    fun fetchPagingProducts(
        offset: Int,
        pageSize: Int,
    ): List<Product>

    fun fetchProducts(): List<Product>

    fun shutdown()
}
