package woowacourse.shopping.ui.fashionlist

import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.utils.QuantityClickListener

interface ProductClickListener : QuantityClickListener {
    fun onClick(product: Product)

    fun onFloatingClick(product: Product)
}
