package woowacourse.shopping.presentation.cart

import woowacourse.shopping.presentation.model.ProductModel

interface CartContract {
    interface Presenter {
        fun initCart()
        fun updateCart()
        fun deleteProduct(productModel: ProductModel)
        fun plusPage()
        fun minusPage()
        fun updateRightPageState()
        fun updateLeftPageState()
    }

    interface View {
        fun initCartItems(productModels: List<ProductModel>)
        fun setCartItems(productModels: List<ProductModel>)
        fun setPage(count: Int)
        fun setRightPageState(isEnable: Boolean)
        fun setLeftPageState(isEnable: Boolean)
    }
}
