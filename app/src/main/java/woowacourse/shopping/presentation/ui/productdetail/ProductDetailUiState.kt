package woowacourse.shopping.presentation.ui.productdetail

import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.ProductHistory

data class ProductDetailUiState(
    val product: Product? = null,
    val isAddToCart: Boolean = false,
    val productHistory: ProductHistory? = null,
)
