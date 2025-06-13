package woowacourse.shopping.view.product.catalog.recentproducts

import woowacourse.shopping.domain.Product

fun interface OnRecentProductEventListener {
    fun itemClick(product: Product)
}
