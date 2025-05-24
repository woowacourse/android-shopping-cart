package woowacourse.shopping.product.catalog.model

import woowacourse.shopping.product.catalog.ProductUiModel

data class PagingData(
    val products: List<ProductUiModel>,
    val hasNext: Boolean,
)
