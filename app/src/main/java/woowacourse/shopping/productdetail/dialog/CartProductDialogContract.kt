package woowacourse.shopping.productdetail.dialog

interface CartProductDialogContract {
    interface Presenter {
        fun decreaseCartProductAmount()
    }

    interface View {
        fun updateCartProductAmount(amount: Int)
    }
}
