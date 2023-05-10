package woowacourse.shopping.cart

import woowacourse.shopping.common.model.CartProductModel
import woowacourse.shopping.domain.CartProduct

interface CartContract {
    interface Presenter {
        fun resumeView()
        fun removeCartProduct(cartProduct: CartProduct)
    }

    interface View {
        fun updateCart(cart: List<CartProductModel>)
    }
}
