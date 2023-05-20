package woowacourse.shopping.productdetail.dialog

interface CartProductDialogContract {
    interface Presenter {
        fun decreaseCartProductAmount()

        fun increaseCartProductAmount()

        fun addToCart()
    }

    interface View {
        fun updateCartProductAmount(amount: Int)

        fun notifyAddToCartCompleted()
    }
}
