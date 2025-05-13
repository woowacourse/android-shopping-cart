package woowacourse.shopping.ui.productlist

import woowacourse.shopping.domain.product.Product

interface ProductClickListener {
    fun onClick(product: Product)
}
