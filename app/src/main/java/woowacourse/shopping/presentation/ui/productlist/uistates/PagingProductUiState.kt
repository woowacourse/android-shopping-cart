package woowacourse.shopping.presentation.ui.productlist.uistates

import woowacourse.shopping.domain.model.PagingProduct

data class PagingProductUiState(
    val pagingProduct: PagingProduct? = null,
    val loading: Boolean = true,
    val failure: Boolean = false,
)
