package woowacourse.shopping.ui.cart

import woowacourse.shopping.ui.model.CartProductModel

interface CartContract {
    interface Presenter {
        fun removeCartProduct(cartProductModel: CartProductModel)

        fun goToPreviousPage()

        fun goToNextPage()

        fun reverseCartProductChecked(cartProductModel: CartProductModel)

        fun updateAllChecked()

        fun decreaseCartProductAmount(cartProductModel: CartProductModel)

        fun increaseCartProductAmount(cartProductModel: CartProductModel)

        fun changeAllChecked(isChecked: Boolean)

        fun checkProductsChanged()
    }

    interface View {
        fun updateCart(cartProducts: List<CartProductModel>, currentPage: Int, isLastPage: Boolean)

        fun updateNavigationVisibility(visibility: Boolean)

        fun updateCartTotalPrice(price: Int)

        fun updateCartTotalAmount(amount: Int)

        fun notifyProductsChanged()

        fun updateCartProduct(prev: CartProductModel, new: CartProductModel)

        fun updateAllChecked(isAllChecked: Boolean)
    }
}
