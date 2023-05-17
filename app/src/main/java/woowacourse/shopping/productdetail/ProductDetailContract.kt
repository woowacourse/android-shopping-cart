package woowacourse.shopping.productdetail

import woowacourse.shopping.model.ProductUiModel

interface ProductDetailContract {

    interface View {

        fun setUpProductDetailView(
            product: ProductUiModel,
            navigateToLatestViewedProductView: () -> Unit,
        )

        fun setUpLatestViewedProductView(product: ProductUiModel)

        fun setUpDialogProductCountView(count: Int)

        fun setUpDialogTotalPriceView(totalPrice: Int)

        fun navigateToShoppingCartView()

        fun navigateToProductDetailView(product: ProductUiModel)
    }

    interface Presenter {

        fun addToShoppingCart()

        fun loadLatestViewedProduct()

        fun plusShoppingCartProductCount(currentCount: Int)

        fun minusShoppingCartProductCount(currentCount: Int)
    }
}
