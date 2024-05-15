package woowacourse.shopping.domain

import woowacourse.shopping.presentation.ui.Product

data class Cart(
    val product: Product,
    val count: Int = DEFAULT_PURCHASE_COUNT,
) {
    companion object {
        const val DEFAULT_PURCHASE_COUNT = 1
    }
}
