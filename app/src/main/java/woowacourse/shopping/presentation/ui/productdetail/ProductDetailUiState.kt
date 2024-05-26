package woowacourse.shopping.presentation.ui.productdetail

import woowacourse.shopping.domain.model.Product

data class ProductDetailUiState(
    val product: Product? = null,
    val productHistory: Product? = null,
    val isLastProductPage: Boolean = false,
)
