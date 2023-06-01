package woowacourse.shopping.cart

import woowacourse.shopping.uimodel.CartProductUIModel

interface CartContract {
    interface View {
        fun setCartProducts(newCartProducts: List<CartProductUIModel>)
        fun showPageNumber(page: Int)
        fun removeAdapterData(cartProductUIModel: CartProductUIModel, position: Int)
        fun setAllChecked(isChecked: Boolean)
        fun setTotalPrice(price: Int)
        fun setOrderProductTypeCount(productTypeCount: Int)
    }

    interface Presenter {
        fun fetchCartProducts()
        fun removeProduct(cartProductUIModel: CartProductUIModel)
        fun goNextPage()
        fun goPreviousPage()
        fun updatePageNumber()
        fun updateProductIsPicked(product: CartProductUIModel, isPicked: Boolean)
        fun updateIsPickAllProduct(isPicked: Boolean)
        fun updateCartProductCount(cartProduct: CartProductUIModel, count: Int)
    }
}
