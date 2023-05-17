package woowacourse.shopping.productdetail

import woowacourse.shopping.model.ProductUiModel

interface ProductDetailContract {

    interface View {

        fun navigateToShoppingCartView()

        fun setUpProductDetailView(
            product: ProductUiModel,
            navigateToLatestViewedProductView: () -> Unit,
        )

        fun setUpLatestViewedProductView(product: ProductUiModel)

        fun navigateToProductDetailView(product: ProductUiModel)
    }

    interface Presenter {

        fun addToShoppingCart()

        fun loadLatestViewedProduct()
    }
}
