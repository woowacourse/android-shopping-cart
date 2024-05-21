package woowacourse.shopping.data.mapper

import woowacourse.shopping.data.model.local.ProductHistoryEntity
import woowacourse.shopping.domain.model.ProductHistory

fun ProductHistoryEntity.toDomain(): ProductHistory {
    return ProductHistory(
        productId = this.productId,
        name = this.name,
        price = this.price,
        imageUrl = this.imageUrl,
        createAt = this.createAt,
    )
}
