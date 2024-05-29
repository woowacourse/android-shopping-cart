package woowacourse.shopping.domain.repository

import woowacourse.shopping.data.model.Product

interface ProductRepository {
    fun fetchCurrentPage(): List<Product>

    fun fetchNextPage(): List<Product>

    fun fetchProduct(id: Long): Product

    fun fetchProducts(ids: List<Long>): List<Product>
}
