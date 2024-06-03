package woowacourse.shopping.domain.datasource

import woowacourse.shopping.domain.model.Product

interface ProductDataSource {
    fun findProductById(id: Int): Result<Product>

    fun getOffsetRanged(
        offset: Int,
        size: Int,
    ): Result<List<Product>>
}
