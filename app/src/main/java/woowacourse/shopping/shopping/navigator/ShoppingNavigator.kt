package woowacourse.shopping.shopping.navigator

import woowacourse.shopping.model.ProductUiModel

interface ShoppingNavigator {

    fun navigateToProductDetailView(
        product: ProductUiModel,
        latestViewedProduct: ProductUiModel?,
    )

    fun navigateToCartView()
}
