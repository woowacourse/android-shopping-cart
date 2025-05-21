package woowacourse.shopping.data.mapper

import woowacourse.shopping.data.dto.HistoryProductDto
import woowacourse.shopping.domain.model.HistoryProduct
import woowacourse.shopping.domain.model.HistoryProduct.Companion.EMPTY_HISTORY_PRODUCT

fun HistoryProductDto.toDomain(): HistoryProduct =
    if (product != null) {
        HistoryProduct(
            productId = product.id,
            name = product.name,
            imageUrl = product.imageUrl,
        )
    } else {
        EMPTY_HISTORY_PRODUCT
    }
