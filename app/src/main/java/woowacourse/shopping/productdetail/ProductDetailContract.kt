package woowacourse.shopping.productdetail

import woowacourse.shopping.model.ProductUiModel

interface ProductDetailContract {

    interface View {

        val productDetailNavigator: ProductDetailNavigator

        fun setUpProductDetailView(product: ProductUiModel)

        fun setUpLatestViewedProductView(product: ProductUiModel?)

        fun setUpDialogTotalPriceView(totalPrice: Int)
    }

    interface Presenter {

        fun addToCart()

        fun loadLatestViewedProduct()

        fun plusCartProductCount()

        fun minusCartProductCount()
    }
}
