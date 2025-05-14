package woowacourse.shopping.view.cart

import woowacourse.shopping.model.products.Product

fun interface OnProductRemoveClickListener {
    fun onRemoveClick(product: Product)
}
