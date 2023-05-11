package woowacourse.shopping.ui.productdetail

interface ProductDetailContract {
    interface View {
        val presenter: Presenter

        fun showBasket()
    }

    interface Presenter {
        val view: View

        fun addBasketProduct()
    }
}
