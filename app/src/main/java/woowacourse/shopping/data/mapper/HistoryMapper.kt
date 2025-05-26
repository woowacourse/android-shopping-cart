package woowacourse.shopping.data.mapper

import woowacourse.shopping.data.model.response.HistoryProductResponse
import woowacourse.shopping.domain.model.HistoryProduct
import woowacourse.shopping.domain.model.HistoryProduct.Companion.EMPTY_HISTORY_PRODUCT

fun HistoryProductResponse.toDomain(): HistoryProduct =
    if (product != null) {
        HistoryProduct(
            productId = product.id,
            name = product.name,
            imageUrl = product.imageUrl,
        )
    } else {
        EMPTY_HISTORY_PRODUCT
    }
