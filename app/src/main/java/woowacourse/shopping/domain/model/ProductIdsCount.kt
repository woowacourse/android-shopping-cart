package woowacourse.shopping.domain.model

import woowacourse.shopping.data.model.ProductIdsCountData

data class ProductIdsCount(
    val productId: Long,
    val quantity: Int,
)

fun ProductIdsCountData.toDomain(): ProductIdsCount = ProductIdsCount(productId, quantity)

fun ProductIdsCount.toData(): ProductIdsCountData = ProductIdsCountData(productId, quantity)
