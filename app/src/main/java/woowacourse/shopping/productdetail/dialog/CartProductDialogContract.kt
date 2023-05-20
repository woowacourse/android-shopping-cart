package woowacourse.shopping.productdetail.dialog

interface CartProductDialogContract {
    interface Presenter {
        fun decreaseCartProductAmount()

        fun increaseCartProductAmount()
    }

    interface View {
        fun updateCartProductAmount(amount: Int)
    }
}
