package woowacourse.shopping.presentation.home

import woowacourse.shopping.data.model.history.RecentProduct
import woowacourse.shopping.data.model.product.CartableProduct

data class HomeUiState(
    val loadStatus: LoadStatus = LoadStatus(),
    val products: List<CartableProduct> = emptyList(),
    val totalQuantity: Int = 0,
    val productHistory: List<RecentProduct> = emptyList(),
)
