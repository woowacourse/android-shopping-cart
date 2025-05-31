package woowacourse.shopping.product.catalog.model

import woowacourse.shopping.product.catalog.ProductUiModel

data class PagingData(
    val products: List<ProductUiModel>,
    val page: Int,
    val hasNext: Boolean,
    val hasPrevious: Boolean,
)
