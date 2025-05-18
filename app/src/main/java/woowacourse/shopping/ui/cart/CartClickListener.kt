package woowacourse.shopping.ui.cart

import woowacourse.shopping.domain.product.Product

fun interface CartClickListener {
    fun onClick(product: Product)
}
