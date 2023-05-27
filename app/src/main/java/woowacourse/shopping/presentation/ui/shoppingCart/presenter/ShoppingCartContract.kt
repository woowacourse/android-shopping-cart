package woowacourse.shopping.presentation.ui.shoppingCart.presenter

import woowacourse.shopping.presentation.ui.common.uimodel.Operator
import woowacourse.shopping.presentation.ui.shoppingCart.uiModel.ProductInCartUiState
import woowacourse.shopping.presentation.ui.shoppingCart.uiModel.ShoppingCartUiState

interface ShoppingCartContract {
    interface View {
        val presenter: Presenter

        fun setShoppingCart(shoppingCart: List<ProductInCartUiState>)
        fun setTotalPrice(shoppingCart: ShoppingCartUiState)
        fun deleteItemInCart(result: Boolean, productId: Long)
        fun setPage(pageNumber: Int)
        fun clickNextPage()
        fun clickPreviousPage()
        fun setPageButtonEnable(previous: Boolean, next: Boolean)
    }

    interface Presenter {
        fun fetchProductsInCartByPage(page: Int)
        fun fetchTotalPriceByCheckAll()
        fun setPageNumber()
        fun goNextPage()
        fun goPreviousPage()
        fun checkPageMovement()
        fun deleteProductInCart(productId: Long)
        fun addCountOfProductInCart(request: Operator, productInCart: ProductInCartUiState)
        fun calculateTotalWithCheck(isChecked: Boolean, productInCart: ProductInCartUiState)
    }
}
