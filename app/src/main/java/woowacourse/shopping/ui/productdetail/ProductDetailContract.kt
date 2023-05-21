package woowacourse.shopping.ui.productdetail

import woowacourse.shopping.ui.model.UiProduct

interface ProductDetailContract {
    interface View {

        fun showBasket()

        fun updateBindingData(product: UiProduct, previousProduct: UiProduct?)

        fun showBasketDialog(
            currentProduct: UiProduct,
            minusClickListener: () -> Unit,
            plusClickListener: () -> Unit,
            updateBasketProduct: () -> Unit
        )

        fun updateProductCount(count: Int)
    }

    interface Presenter {
        val view: View

        fun setBasketDialog()

        fun selectPreviousProduct()

        fun initProductData()
    }
}
