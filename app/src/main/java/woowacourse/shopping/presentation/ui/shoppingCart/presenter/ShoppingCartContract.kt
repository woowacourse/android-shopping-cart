package woowacourse.shopping.presentation.ui.shoppingCart.presenter

import woowacourse.shopping.domain.model.ProductInCart

interface ShoppingCartContract {
    interface View {
        val presenter: Presenter

        fun setShoppingCart(shoppingCart: List<ProductInCart>)
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
    }
}
