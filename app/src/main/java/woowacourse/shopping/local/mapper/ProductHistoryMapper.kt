package woowacourse.shopping.local.mapper

import woowacourse.shopping.data.model.local.ProductHistoryDto
import woowacourse.shopping.local.model.ProductHistoryEntity

fun ProductHistoryEntity.toData(): ProductHistoryDto {
    return ProductHistoryDto(
        productId = this.productId,
        name = this.name,
        price = this.price,
        imageUrl = this.imageUrl,
        createAt = this.createAt,
    )
}
