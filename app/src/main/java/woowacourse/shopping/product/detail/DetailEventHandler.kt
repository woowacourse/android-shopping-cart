package woowacourse.shopping.product.detail

import woowacourse.shopping.product.catalog.ProductUiModel

interface DetailEventHandler {
    fun onAddCartItem(product: ProductUiModel)
}
