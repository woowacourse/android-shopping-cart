package woowacourse.shopping.presentation.ui.productDetail

import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.presentation.ui.common.QuantityControlClickListener
import woowacourse.shopping.presentation.ui.shoppingCart.uiModel.ProductInCartUiState

interface ProductDetailClickListener : QuantityControlClickListener {

    fun setClickEventOnToShoppingCart(
        product: ProductInCartUiState,
        onClick: ProductDetailClickListener,
    )

    fun setClickEventOnLastViewed(lastViewedProduct: Product)

    override fun setClickEventOnOperatorButton(
        operator: Boolean,
        productInCart: ProductInCartUiState,
    )
}
