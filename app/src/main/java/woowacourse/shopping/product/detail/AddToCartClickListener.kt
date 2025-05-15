package woowacourse.shopping.product.detail

import woowacourse.shopping.product.catalog.ProductUiModel

fun interface AddToCartClickListener {
    fun onClick(product: ProductUiModel)
}
