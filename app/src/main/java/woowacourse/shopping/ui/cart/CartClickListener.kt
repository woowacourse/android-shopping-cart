package woowacourse.shopping.ui.cart

import woowacourse.shopping.domain.product.Product

interface CartClickListener {
    fun onClick(product: Product)
}
