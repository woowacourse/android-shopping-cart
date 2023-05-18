package woowacourse.shopping.ui.productdetail

import woowacourse.shopping.ui.model.UiProduct

interface ProductDetailContract {
    interface View {

        fun showBasket()

        fun initBindingData(product: UiProduct)
    }

    interface Presenter {
        val view: View

        fun addBasketProduct(): Thread

        fun initProductData()
    }
}
