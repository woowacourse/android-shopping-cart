package woowacourse.shopping.product.catalog

data class PagingData(
    val products: List<ProductUiModel>,
    val hasNext: Boolean,
)
