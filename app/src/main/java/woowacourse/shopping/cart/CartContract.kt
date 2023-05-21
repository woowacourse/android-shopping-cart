package woowacourse.shopping.cart

import woowacourse.shopping.common.model.CartProductModel

interface CartContract {
    interface Presenter {
        fun removeCartProduct(cartProductModel: CartProductModel)

        fun goToPreviousPage()

        fun goToNextPage()

        fun changeCartProductChecked(cartProductModel: CartProductModel)

        fun updateAllChecked()
    }

    interface View {
        fun updateCart(cartProducts: List<CartProductModel>, currentPage: Int, isLastPage: Boolean)

        fun updateNavigationVisibility(visibility: Boolean)

        fun updateCartTotalPrice(price: Int)

        fun updateCartTotalAmount(amount: Int)

        fun setResultForChange()

        fun updateCartProduct(prev: CartProductModel, new: CartProductModel)

        fun updateAllChecked(isAllChecked: Boolean)
    }
}
