package woowacourse.shopping.product.detail

import woowacourse.shopping.product.catalog.ProductUiModel

fun interface LatestViewedProductClickListener {
    fun onClick(product: ProductUiModel)
}
