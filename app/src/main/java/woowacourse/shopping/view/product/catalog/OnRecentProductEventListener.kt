package woowacourse.shopping.view.product.catalog

import woowacourse.shopping.domain.Product

fun interface OnRecentProductEventListener {
    fun itemClick(product: Product)
}
