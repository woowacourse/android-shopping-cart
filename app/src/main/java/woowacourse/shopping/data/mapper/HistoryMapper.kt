package woowacourse.shopping.data.mapper

import woowacourse.shopping.data.dto.ExploreHistoryProductDto
import woowacourse.shopping.domain.model.HistoryProduct
import woowacourse.shopping.domain.model.HistoryProduct.Companion.EMPTY_HISTORY_PRODUCT

fun ExploreHistoryProductDto.toDomain(): HistoryProduct =
    if (product != null) {
        HistoryProduct(
            productId = product.id,
            name = product.name,
            imageUrl = product.imageUrl,
        )
    } else {
        EMPTY_HISTORY_PRODUCT
    }
