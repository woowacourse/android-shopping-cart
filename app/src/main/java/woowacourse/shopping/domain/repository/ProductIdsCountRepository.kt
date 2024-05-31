package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.ProductIdsCount

interface ProductIdsCountRepository {
    fun findByProductId(productId: Long): ProductIdsCount

    fun findByProductIdAsync(
        productId: Long,
        callback: (ProductIdsCount) -> Unit,
    )
}
