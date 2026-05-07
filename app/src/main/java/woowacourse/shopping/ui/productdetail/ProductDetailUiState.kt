package woowacourse.shopping.ui.productdetail

import woowacourse.shopping.model.Product

data class ProductDetailUiState(
    val product: Product? = null,
    val quantity: Int = 0,
    val isAdding: Boolean = false,
)
