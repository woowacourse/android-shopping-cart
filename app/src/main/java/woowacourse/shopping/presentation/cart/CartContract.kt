package woowacourse.shopping.presentation.cart

import woowacourse.shopping.presentation.model.CartProductModel
import woowacourse.shopping.presentation.model.ProductModel

interface CartContract {
    interface Presenter {
        fun loadCart()
        fun deleteCartProduct(cartProductModel: CartProductModel)
        fun subProductCartCount(cartProductModel: CartProductModel)
        fun addProductCartCount(cartProductModel: CartProductModel)
        fun changeProductSelected(productModel: ProductModel)
        fun selectAllProduct()
        fun unselectAllProduct()
        fun plusPage()
        fun minusPage()
    }

    interface View {
        fun showCartProductModels(cartProductModels: List<CartProductModel>)
        fun showPageNumber(count: Int)
        fun showRightPageIsEnabled(isEnable: Boolean)
        fun showLeftPageIsEnabled(isEnable: Boolean)
        fun showTotalPrice(price: Int)
        fun showTotalCount(count: Int)
        fun showAllCheckBoxIsChecked(isChecked: Boolean)
    }
}
