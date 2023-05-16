package woowacourse.shopping.ui.productdetail

interface ProductDetailContract {
    interface View {

        fun showBasket()
    }

    interface Presenter {
        val view: View

        fun addBasketProduct()
    }
}
