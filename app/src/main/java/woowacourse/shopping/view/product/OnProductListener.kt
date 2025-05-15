package woowacourse.shopping.view.product

import woowacourse.shopping.domain.Product

fun interface OnProductListener {
    fun onItemClick(product: Product)
}
