package woowacourse.shopping.data.remote.api

import woowacourse.shopping.domain.model.Product

interface ProductApi {
    fun findById(id: Int): Result<Product>

    fun findByOffsetAndSize(
        offset: Int,
        size: Int,
    ): Result<List<Product>>
}
