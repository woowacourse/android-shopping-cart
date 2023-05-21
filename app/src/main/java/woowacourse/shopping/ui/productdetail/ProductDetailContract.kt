package woowacourse.shopping.ui.productdetail

import woowacourse.shopping.ui.model.UiProduct

interface ProductDetailContract {
    interface View {

        fun showBasket()

        fun updateBindingData(product: UiProduct, previousProduct: UiProduct?)

        fun showBasketDialog(curentProduct: UiProduct)

        fun updateProductCount(count: Int)
    }

    interface Presenter {
        val view: View

        fun setBasketDialog()

        fun selectPreviousProduct()

        fun addBasketProduct(): Thread

        fun initProductData()
    }
}
