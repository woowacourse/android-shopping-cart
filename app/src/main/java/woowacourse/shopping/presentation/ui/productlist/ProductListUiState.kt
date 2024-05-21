package woowacourse.shopping.presentation.ui.productlist

import woowacourse.shopping.domain.model.PagingProduct

data class ProductListUiState(
    val pagingProduct: PagingProduct? = null,
)
