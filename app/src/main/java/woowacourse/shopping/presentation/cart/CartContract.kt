package woowacourse.shopping.presentation.cart

import woowacourse.shopping.presentation.model.CartProductInfoModel

interface CartContract {
    interface Presenter {

        fun checkPlusPageAble()
        fun checkMinusPageAble()
        fun addProductInOrder(cartProductModel: CartProductInfoModel)
        fun deleteProductInOrder(cartProductModel: CartProductInfoModel)
        fun updateProductCount(cartProductModel: CartProductInfoModel, count: Int)
        fun deleteProductItem(cartProductModel: CartProductInfoModel)
        fun plusPage()
        fun minusPage()
        fun refreshCurrentPage()
        fun changeCurrentPageProductsOrder(isOrdered: Boolean)
        fun checkCurrentPageProductsOrderState()
        fun updateOrderPrice()
        fun updateOrderCount()
        fun updateProductPrice(cartProductModel: CartProductInfoModel)
    }

    interface View {
        fun setCartItems(productModels: List<CartProductInfoModel>)
        fun setUpPlusPageState(isEnable: Boolean)
        fun setUpMinusPageState(isEnable: Boolean)
        fun setOrderPrice(totalPrice: Int)
        fun setOrderCount(count: Int)
        fun setAllOrderState(isAllOrder: Boolean)
        fun setProductPrice(price: Int)
        fun setPage(page: String)
    }
}
