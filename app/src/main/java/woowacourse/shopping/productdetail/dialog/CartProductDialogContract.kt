package woowacourse.shopping.productdetail.dialog

interface CartProductDialogContract {
    interface Presenter

    interface View {
        fun setupCartProductAmount(amount: Int)
    }
}
