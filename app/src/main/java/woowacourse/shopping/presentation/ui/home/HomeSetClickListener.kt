package woowacourse.shopping.presentation.ui.home

import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.presentation.ui.common.QuantityControlClickListener
import woowacourse.shopping.presentation.ui.home.uiModel.ProductInCartUiState

interface HomeSetClickListener : QuantityControlClickListener {

    fun setClickEventOnProduct(product: Product)
    fun setClickEventOnShowMoreButton()
    override fun setClickEventOnOperatorButton(
        operator: Boolean,
        productInCart: ProductInCartUiState,
    )
}
