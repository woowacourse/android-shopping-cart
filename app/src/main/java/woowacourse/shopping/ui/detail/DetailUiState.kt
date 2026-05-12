package woowacourse.shopping.ui.detail

import woowacourse.shopping.ui.model.ProductUiModel

data class DetailUiState(
    val product: ProductUiModel = ProductUiModel(),
    val quantity: Int = 1,
    val totalPrice: Int = 0,
    val recentItem: ProductUiModel? = null,
)
