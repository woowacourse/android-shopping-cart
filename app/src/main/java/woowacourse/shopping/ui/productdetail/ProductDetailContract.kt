package woowacourse.shopping.ui.productdetail

import woowacourse.shopping.ui.model.UiProduct

interface ProductDetailContract {
    interface View {

        fun showBasket()

        fun updateBindingData(product: UiProduct, previousProduct: UiProduct?)

        fun showBasketDialog(
            currentProduct: UiProduct
        )

        fun updateProductCount(count: Int)
    }

    interface Presenter {
        val view: View

        fun setBasketDetailData()

        fun selectPreviousProduct()

        fun initProductData()

        fun minusProductCount()

        fun plusProductCount()

        fun updateBasketProduct()
    }
}
