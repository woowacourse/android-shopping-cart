package woowacourse.shopping.presentation.cart

import woowacourse.shopping.presentation.model.CartProductModel
import woowacourse.shopping.presentation.model.ProductModel

interface CartContract {
    interface Presenter {
        fun loadCart()
        fun deleteProduct(productModel: ProductModel)
        fun subProductCount(productModel: ProductModel)
        fun addProductCount(productModel: ProductModel)
        fun changeProductSelected(productModel: ProductModel)

        fun plusPage()
        fun minusPage()
    }

    interface View {
        fun setCartProductModels(cartProductModels: List<CartProductModel>)
        fun setPage(count: Int)
        fun setRightPageEnable(isEnable: Boolean)
        fun setLeftPageEnable(isEnable: Boolean)
        fun setTotalPrice(price: Int)
        fun setTotalCount(count: Int)
    }
}
