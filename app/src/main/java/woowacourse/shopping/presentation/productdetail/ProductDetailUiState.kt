package woowacourse.shopping.presentation.productdetail

import woowacourse.shopping.domain.model.product.RecentlyViewedProducts

data class ProductDetailUiState(
    val quantity: Int = 1,
    val recentlyViewedProducts: RecentlyViewedProducts = RecentlyViewedProducts(),
)
