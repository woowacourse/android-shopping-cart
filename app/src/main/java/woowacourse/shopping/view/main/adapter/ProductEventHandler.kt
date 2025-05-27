package woowacourse.shopping.view.main.adapter

import android.view.ViewGroup
import woowacourse.shopping.view.base.QuantitySelectorEventHandler
import woowacourse.shopping.view.uimodel.ProductUiModel

interface ProductEventHandler : QuantitySelectorEventHandler {
    fun onProductSelected(productUiModel: ProductUiModel)

    fun onBtnItemProductAddToCartSelected(productUiModel: ProductUiModel)

    fun whenQuantityChangedSelectView(
        view: ViewGroup,
        productUiModel: ProductUiModel,
    )
}
