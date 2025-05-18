package woowacourse.shopping.ui.productdetail

import woowacourse.shopping.domain.product.Product

fun interface DetailClickListener {
    fun onAddToCartClick(product: Product)
}
