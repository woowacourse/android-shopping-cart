package woowacourse.shopping.ui.fashionlist

import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.utils.QuantityClickListener

interface ProductClickListener {
    fun onClick(product: Product)
}
