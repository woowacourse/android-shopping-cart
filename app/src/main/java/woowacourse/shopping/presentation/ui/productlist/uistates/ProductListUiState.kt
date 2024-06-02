package woowacourse.shopping.presentation.ui.productlist.uistates

import woowacourse.shopping.domain.model.Order
import woowacourse.shopping.domain.model.PagingProduct

data class ProductListUiState(
    val pagingProduct: PagingProduct? = null,
    val orders: List<Order>? = null,
    val orderSum: Int? = null,
)
