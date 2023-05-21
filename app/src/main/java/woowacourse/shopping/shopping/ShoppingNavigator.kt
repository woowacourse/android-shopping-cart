package woowacourse.shopping.shopping

import woowacourse.shopping.model.ProductUiModel

interface ShoppingNavigator {

    fun navigateToProductDetailView(
        product: ProductUiModel,
        latestViewedProduct: ProductUiModel?,
    )

    fun navigateToCartView()
}
