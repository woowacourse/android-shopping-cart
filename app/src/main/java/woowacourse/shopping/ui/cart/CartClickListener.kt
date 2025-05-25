package woowacourse.shopping.ui.cart

import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.ui.QuantityClickListener

interface CartClickListener : QuantityClickListener {
    fun onClick(product: Product)
}
