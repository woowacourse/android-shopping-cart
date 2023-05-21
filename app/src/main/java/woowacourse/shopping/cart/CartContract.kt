package woowacourse.shopping.cart

import woowacourse.shopping.uimodel.CartProductUIModel

interface CartContract {
    interface View {
        fun setCartProducts(newCartProducts: List<CartProductUIModel>)
        fun showPageNumber(page: Int)
        fun removeAdapterData(cartProductUIModel: CartProductUIModel, position: Int)
        fun refreshAllChecked(isChecked: Boolean)
    }

    interface Presenter {
        fun getCartProducts()
        fun removeProduct(cartProductUIModel: CartProductUIModel, position: Int)
        fun goNextPage()
        fun goPreviousPage()
        fun setPageNumber()
        fun changePage(page: Int)
        fun updateProductIsPicked(product: CartProductUIModel, isPicked: Boolean)
        fun calculateTotalPrice()
        fun updateIsPickAllProduct(isPicked: Boolean)
        fun updateAllChecked()
        fun updateCountOfProductType()
        fun updateCartProductCount(cartProduct: CartProductUIModel, count: Int)
    }
}
