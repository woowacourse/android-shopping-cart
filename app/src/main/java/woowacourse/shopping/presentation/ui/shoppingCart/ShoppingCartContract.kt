package woowacourse.shopping.presentation.ui.shoppingCart

import woowacourse.shopping.domain.model.ProductInCart

interface ShoppingCartContract {
    interface View {
        val presenter: Presenter

        fun setShoppingCart(shoppingCart: List<ProductInCart>)
        fun setPage(pageNumber: Int)
        fun setPageButtonEnable(previous: Boolean, next: Boolean)
        fun deleteProduct(index: Int)
    }

    interface Presenter {
        fun getShoppingCart()
        fun setPageNumber()
        fun goNextPage()
        fun goPreviousPage()
        fun checkPageMovement()
        fun deleteProductInCart(index: Int)
    }
}
