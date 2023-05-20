package woowacourse.shopping.cart

import woowacourse.shopping.common.model.CartProductModel

interface CartContract {
    interface Presenter {
        fun removeCartProduct(cartProductModel: CartProductModel)

        fun goToPreviousPage()

        fun goToNextPage()
    }

    interface View {
        fun updateCart(cartProducts: List<CartProductModel>, currentPage: Int, isLastPage: Boolean)

        fun updateNavigationVisibility(visibility: Boolean)

        fun updateCartTotalPrice(price: Int)

        fun updateCartTotalAmount(amount: Int)

        fun setResultForChange()
    }
}
