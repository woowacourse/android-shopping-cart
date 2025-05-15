package woowacourse.shopping.data

import woowacourse.shopping.domain.Product

interface ProductRepository {
    fun getAll(): List<Product>

    fun getPaged(
        limit: Int,
        offset: Int,
    ): List<Product>
}
