package woowacourse.shopping.productdetail

import woowacourse.shopping.model.ProductUiModel

interface ProductDetailNavigator {

    fun navigateToCartView()

    fun navigateToProductDetailView(product: ProductUiModel)
}
