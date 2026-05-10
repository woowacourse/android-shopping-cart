package woowacourse.shopping.ui.screens.productdetail

import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.RecentProduct

data class ProductDetailUiState(
    val product: Product? = null,
    val latestProduct: RecentProduct? = null,
    val isLoading: Boolean = false,
    val amount: Int = 1,
    val isError: Boolean = false,
)
