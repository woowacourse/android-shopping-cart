package woowacourse.shopping.ui.productdetail

import woowacourse.shopping.model.Product

data class ProductDetailUiState(
    val isLoading: Boolean = false,
    val product: Product? = null
)
