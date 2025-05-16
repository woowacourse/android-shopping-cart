package woowacourse.shopping.data

import woowacourse.shopping.domain.Product

interface ProductStorage {
    operator fun get(id: Long): Product

    fun getProducts(
        page: Int,
        pageSize: Int,
    ): List<Product>

    fun hasMoreProduct(
        page: Int,
        pageSize: Int,
    ): Boolean
}
