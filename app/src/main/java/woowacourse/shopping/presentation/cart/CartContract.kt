package woowacourse.shopping.presentation.cart

import woowacourse.shopping.presentation.model.ProductModel

interface CartContract {
    interface Presenter {
        fun initCart()
        fun deleteProduct(productModel: ProductModel)
        fun plusPage()
        fun minusPage()
    }

    interface View {
        fun setCartProductModels(productModels: List<ProductModel>)
        fun setPage(count: Int)
        fun setRightPageEnable(isEnable: Boolean)
        fun setLeftPageEnable(isEnable: Boolean)
    }
}
