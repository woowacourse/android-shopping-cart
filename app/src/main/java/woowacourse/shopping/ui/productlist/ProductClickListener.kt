package woowacourse.shopping.ui.productlist

import woowacourse.shopping.domain.product.Product

fun interface ProductClickListener {
    fun onClick(product: Product)
}
