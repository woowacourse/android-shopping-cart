package woowacourse.shopping.ui.screens.productdetail

import woowacourse.shopping.domain.Product

data class ProductDetailUiState(
    val product: Product? = null,
    val isLoading: Boolean = false,
    val amount: Int = 1,
    val isError: Boolean = false,
)
