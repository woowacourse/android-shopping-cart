package woowacourse.shopping.presentation.ui.common

import woowacourse.shopping.presentation.ui.home.uiModel.ProductInCartUiState

interface QuantityControlClickListener {

    fun setClickEventOnOperatorButton(operator: Boolean, productInCart: ProductInCartUiState)
}
