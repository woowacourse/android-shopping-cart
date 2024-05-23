package woowacourse.shopping.domain.model

import woowacourse.shopping.data.ProductIdsCountData

data class ProductIdsCount(
    val productId: Int,
    val quantity: Int,
) {
    init {
        require(quantity > 0) { "quantity must be greater than 0" }
    }
}

fun ProductIdsCountData.toDomain(): ProductIdsCount = ProductIdsCount(productId, quantity)

fun ProductIdsCount.toData(): ProductIdsCountData = ProductIdsCountData(productId, quantity)
