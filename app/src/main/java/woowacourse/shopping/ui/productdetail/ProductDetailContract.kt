package woowacourse.shopping.ui.productdetail

interface ProductDetailContract {
    interface View {
        val presenter: Presenter
    }

    interface Presenter {
        val view: View
    }
}
