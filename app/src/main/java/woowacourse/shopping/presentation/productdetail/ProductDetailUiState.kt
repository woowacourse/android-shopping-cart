package woowacourse.shopping.presentation.productdetail

import woowacourse.shopping.domain.model.product.Product
import woowacourse.shopping.domain.model.product.RecentlyViewedProducts

data class ProductDetailUiState(
    val quantity: Int = 1,
    val lastViewedProduct: Product? = null,
    val recentlyViewedProducts: RecentlyViewedProducts = RecentlyViewedProducts(),
)
