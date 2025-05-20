package woowacourse.shopping.ui.fashionlist

import woowacourse.shopping.domain.product.Product

interface ProductClickListener {
    fun onClick(product: Product)
}
