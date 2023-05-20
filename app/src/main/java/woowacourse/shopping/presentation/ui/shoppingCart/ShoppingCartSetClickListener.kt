package woowacourse.shopping.presentation.ui.shoppingCart

import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.presentation.ui.common.QuantityControlClickListener
import woowacourse.shopping.presentation.ui.home.uiModel.ProductInCartUiState

interface ShoppingCartSetClickListener : QuantityControlClickListener {

    fun setClickEventOnItem(productInCart: ProductInCartUiState)
    fun setClickEventOnDeleteButton(productInCart: ProductInCartUiState)
    override fun setClickEventOnOperatorButton(operator: Boolean, productInCart: Product)
}
