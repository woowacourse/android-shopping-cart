package woowacourse.shopping.presentation.ui.productDetail.dialog.presenter

import woowacourse.shopping.presentation.ui.common.BaseView
import woowacourse.shopping.presentation.ui.common.uimodel.Operator
import woowacourse.shopping.presentation.ui.shoppingCart.uiModel.ProductInCartUiState

interface ProductDetailDialogContract {
    interface View : BaseView<Presenter> {
        fun setCount(product: ProductInCartUiState)
    }

    interface Presenter {
        fun addProductInCart(product: ProductInCartUiState)
        fun addCountOfProductInCart(request: Operator, productInCart: ProductInCartUiState)
    }
}
