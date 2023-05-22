package woowacourse.shopping.presentation.ui.shoppingCart.presenter

import woowacourse.shopping.presentation.ui.common.uimodel.Operator
import woowacourse.shopping.presentation.ui.home.uiModel.ProductInCartUiState

interface ShoppingCartContract {
    interface View {
        val presenter: Presenter

        fun setShoppingCart(shoppingCart: List<ProductInCartUiState>)
        fun setPage(pageNumber: Int)
        fun clickNextPage()
        fun clickPreviousPage()
        fun setPageButtonEnable(previous: Boolean, next: Boolean)
    }

    interface Presenter {
        fun getShoppingCart(page: Int)
        fun setPageNumber()
        fun goNextPage()
        fun goPreviousPage()
        fun checkPageMovement()
        fun deleteProductInCart(productId: Long): Boolean
        fun addCountOfProductInCart(request: Operator, productInCart: ProductInCartUiState)
        fun calculateTotalWithCheck(isChecked: Boolean, productInCart: ProductInCartUiState)
    }
}
