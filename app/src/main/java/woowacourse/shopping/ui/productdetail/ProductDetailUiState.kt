package woowacourse.shopping.ui.productdetail

import woowacourse.shopping.model.Product

data class ProductDetailUiState(
    val product: Product? = null,
    val isAdding: Boolean = false,
    val isAdded: Boolean = false,
)
