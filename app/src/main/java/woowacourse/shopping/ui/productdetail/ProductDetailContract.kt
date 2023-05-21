package woowacourse.shopping.ui.productdetail

import woowacourse.shopping.ui.model.UiProduct

interface ProductDetailContract {
    interface View {

        fun showBasket()

        fun updateBindingData(product: UiProduct, previousProduct: UiProduct?)
    }

    interface Presenter {
        val view: View

        fun selectPreviousProduct()

        fun addBasketProduct(): Thread

        fun initProductData()
    }
}
