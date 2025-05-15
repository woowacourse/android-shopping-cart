package woowacourse.shopping.ui.productdetail

import woowacourse.shopping.domain.product.Product

interface DetailClickListener {
    fun onAddToCartClick(product: Product)
}
