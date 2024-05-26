package woowacourse.shopping.data.datasource

import woowacourse.shopping.data.entity.ProductEntity

interface ProductDataSource {
    fun products(): List<ProductEntity>

    fun productsByOffset(
        startPosition: Int,
        offset: Int,
    ): List<ProductEntity>

    fun productById(productId: Long): ProductEntity
}
