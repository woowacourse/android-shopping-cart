package woowacourse.shopping.cart

import woowacourse.shopping.common.model.CartProductModel

interface CartContract {
    interface Presenter {
        fun removeCartProduct(cartProductModel: CartProductModel)

        fun goToPreviousPage()

        fun goToNextPage()
    }

    interface View {
        fun updateCart(cartProductsModel: List<CartProductModel>)
        fun updateNavigator(currentPage: Int, maxPage: Int)
    }
}
