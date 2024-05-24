package woowacourse.shopping.domain.model

import woowacourse.shopping.data.ProductIdsCountData

data class ProductIdsCount(
    val productId: Int,
    val quantity: Int,
)

fun ProductIdsCountData.toDomain(): ProductIdsCount = ProductIdsCount(productId, quantity)

fun ProductIdsCount.toData(): ProductIdsCountData = ProductIdsCountData(productId, quantity)
