package woowacourse.shopping.features.productList

import woowacourse.shopping.features.productList.uiModel.ProductUiModel

data class ProductListUiState(
    val productList: List<ProductUiModel> = emptyList(),
    val recentProductList: List<ProductUiModel> = emptyList(),
    val totalCartItemsCount: Int = 0,
    val isLastPage: Boolean = false,
)
