package woowacourse.shopping.ui.productdetail.dialog

import woowacourse.shopping.ui.model.ShoppingProductModel

interface CartProductDialogContract {
    interface Presenter {
        fun decreaseCartProductAmount()

        fun increaseCartProductAmount()

        fun addToCart()
    }

    interface View {
        fun updateCartProductAmount(amount: Int)

        fun notifyAddToCartCompleted()

        fun notifyProductChanged(product: ShoppingProductModel, amountDifference: Int)
    }
}
