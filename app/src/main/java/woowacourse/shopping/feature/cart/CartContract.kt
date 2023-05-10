package woowacourse.shopping.feature.cart

import woowacourse.shopping.model.CartProductUiModel

interface CartContract {
    interface View {
        fun changeCartProducts(newItems: List<CartProductItemModel>)
        fun deleteCartProductFromScreen(position: Int)
    }

    interface Presenter {
        fun loadCartProduct()
        fun deleteCartProduct(cartProduct: CartProductUiModel)
    }
}
