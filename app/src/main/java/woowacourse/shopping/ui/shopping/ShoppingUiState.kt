package woowacourse.shopping.ui.shopping

import woowacourse.shopping.model.Products
import woowacourse.shopping.ui.common.model.ProductUiModel

data class ShoppingUiState(
    val isLoading: Boolean = false,
    val visibleCount: Int = 0,
    val visibleProducts: List<ProductUiModel> = emptyList(),
    val recentProducts: Products = Products(emptyList()),
    val hasNext: Boolean = false,
    val sizeInRepo: Int = 0,
    val cartCount: Int = 0,
)
