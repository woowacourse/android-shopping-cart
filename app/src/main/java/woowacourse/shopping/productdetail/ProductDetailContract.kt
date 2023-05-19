package woowacourse.shopping.productdetail

import woowacourse.shopping.model.ProductUiModel

interface ProductDetailContract {

    interface View {

        fun setUpProductDetailView(
            product: ProductUiModel,
            navigateToLatestViewedProductView: () -> Unit,
        )

        fun setUpLatestViewedProductView(product: ProductUiModel?)

        fun setUpDialogTotalPriceView(totalPrice: Int)

        fun navigateToCartView()

        fun navigateToProductDetailView(product: ProductUiModel)
    }

    interface Presenter {

        fun addToCart()

        fun loadLatestViewedProduct()

        fun plusCartProductCount()

        fun minusCartProductCount()
    }
}
