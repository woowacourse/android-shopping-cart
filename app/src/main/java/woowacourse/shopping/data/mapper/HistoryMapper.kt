package woowacourse.shopping.data.mapper

import woowacourse.shopping.data.dto.ExploreHistoryProductDto
import woowacourse.shopping.domain.model.HistoryProduct

fun ExploreHistoryProductDto.toDomain(): HistoryProduct =
    HistoryProduct(
        productId = product.id,
        name = product.name,
        imageUrl = product.imageUrl,
    )
