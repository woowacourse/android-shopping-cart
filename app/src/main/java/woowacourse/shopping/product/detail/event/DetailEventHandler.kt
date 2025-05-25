package woowacourse.shopping.product.detail.event

import woowacourse.shopping.product.catalog.ProductUiModel

interface DetailEventHandler {
    fun onAddCartItem(product: ProductUiModel)

    fun onProductClick(product: ProductUiModel)
}
