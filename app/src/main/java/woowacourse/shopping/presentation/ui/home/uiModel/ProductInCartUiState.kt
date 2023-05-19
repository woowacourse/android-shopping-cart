package woowacourse.shopping.presentation.ui.home.uiModel

import woowacourse.shopping.domain.model.Product

data class ProductInCartUiState(
    val product: Product,
    val quantity: Int,
) {
    fun isContained(): Boolean = quantity > 0
    fun isDeleted(): Boolean = quantity < 1
}
