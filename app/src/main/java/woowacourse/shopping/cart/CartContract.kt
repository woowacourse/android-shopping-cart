package woowacourse.shopping.cart

import woowacourse.shopping.uimodel.CartProductUIModel

interface CartContract {
    interface View {
        fun setCartProducts(newCartProducts: List<CartProductUIModel>)
        fun showPageNumber(page: Int)
        fun removeAdapterData(cartProductUIModel: CartProductUIModel, position: Int)
    }

    interface Presenter {
        fun removeProduct(cartProductUIModel: CartProductUIModel, position: Int)
        fun getCartProducts()
        fun goNextPage()
        fun goPreviousPage()
        fun setPageNumber()
        fun changePage(page: Int)
        fun updateProductIsPicked(product: CartProductUIModel, isPicked: Boolean)
    }
}
