package woowacourse.shopping.cart

import woowacourse.shopping.common.model.CartProductModel

interface CartContract {
    interface Presenter {
        fun removeCartProduct(cartProductModel: CartProductModel)
    }

    interface View {
        fun updateCart(cart: List<CartProductModel>)
    }
}
