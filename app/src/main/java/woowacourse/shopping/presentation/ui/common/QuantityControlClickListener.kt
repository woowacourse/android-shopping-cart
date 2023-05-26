package woowacourse.shopping.presentation.ui.common

import woowacourse.shopping.presentation.ui.shoppingCart.uiModel.ProductInCartUiState

interface QuantityControlClickListener {

    fun setClickEventOnOperatorButton(operator: Boolean, productInCart: ProductInCartUiState)
}
