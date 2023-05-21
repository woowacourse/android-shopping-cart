package woowacourse.shopping.productdetail.navigator

import woowacourse.shopping.model.ProductUiModel

interface ProductDetailNavigator {

    fun navigateToCartView()

    fun navigateToProductDetailView(product: ProductUiModel)
}
