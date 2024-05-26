package woowacourse.shopping.data.okhttp.api

import woowacourse.shopping.domain.model.Product

interface ProductApi {
    fun findById(id: Int): Result<Product>

    fun findByOffsetAndSize(
        offset: Int,
        size: Int,
    ): Result<List<Product>>
}
