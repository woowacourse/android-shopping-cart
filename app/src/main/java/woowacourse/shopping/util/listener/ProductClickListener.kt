package woowacourse.shopping.util.listener

import woowacourse.shopping.model.Product

interface ProductClickListener {
    fun onProductClick(product: Product)
    fun onPlusProductClick(product: Product)
}
