package woowacourse.shopping.presentation.cart

import woowacourse.shopping.presentation.model.ProductModel

interface CartContract {
    interface Presenter {
        fun updateCart()
        fun deleteProduct(productModel: ProductModel)
        fun plusPage()
        fun minusPage()
        fun updatePlusButtonState()
        fun updateMinusButtonState()
    }

    interface View {
        fun setCartItems(productModels: List<ProductModel>)
        fun setPage(count: Int)
        fun setUpPlusPageButtonState(isEnable: Boolean)
        fun setUpMinusPageButtonState(isEnable: Boolean)
    }
}
