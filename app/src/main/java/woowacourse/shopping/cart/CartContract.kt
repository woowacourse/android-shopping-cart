package woowacourse.shopping.cart

import woowacourse.shopping.common.model.CartProductModel

interface CartContract {
    interface Presenter {
        fun removeCartProduct(cartProductModel: CartProductModel)

        fun goToPreviousPage()

        fun goToNextPage()
    }

    interface View {
        fun updateCart(cart: List<CartProductModel>, currentPage: Int, isNextButtonEnabled: Boolean)

        fun updateNavigationVisibility(visibility: Boolean)
    }
}
