package woowacourse.shopping.presentation.ui.productDetail.dialog

import woowacourse.shopping.presentation.ui.common.QuantityControlClickListener
import woowacourse.shopping.presentation.ui.shoppingCart.uiModel.ProductInCartUiState

interface ProductDetailDialogClickListener : QuantityControlClickListener {
    override fun setClickEventOnOperatorButton(
        operator: Boolean,
        productInCart: ProductInCartUiState,
    )

    fun setClickEventOnToShoppingCart(product: ProductInCartUiState)
}
