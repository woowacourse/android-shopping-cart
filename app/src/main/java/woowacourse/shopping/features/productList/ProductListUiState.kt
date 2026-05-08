package woowacourse.shopping.features.productList

data class ProductListUiState(
    val productList: List<ProductUiModel> = emptyList(),
    val totalCartItemsCount: Int = 0,
    val isLastPage: Boolean = false,
)
