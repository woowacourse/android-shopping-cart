package woowacourse.shopping.presentation.ui.productlist.uistates

import woowacourse.shopping.domain.model.ProductBrowsingHistory

sealed class ProductBrowsingHistoryUiState {
    class Success(
        val histories: List<ProductBrowsingHistory>? = null,
    ) : ProductBrowsingHistoryUiState()

    data object Loading : ProductBrowsingHistoryUiState()

    data object Failure : ProductBrowsingHistoryUiState()
}
