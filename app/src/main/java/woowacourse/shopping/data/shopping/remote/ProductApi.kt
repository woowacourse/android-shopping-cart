package woowacourse.shopping.data.shopping.remote

import woowacourse.shopping.domain.Product

interface ProductApi {
    fun findById(id: Int): Result<Product>

    fun findByOffset(
        offset: Int,
        size: Int,
    ): Result<List<Product>>
}
