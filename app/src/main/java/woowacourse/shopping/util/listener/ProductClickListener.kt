package woowacourse.shopping.util.listener

import woowacourse.shopping.model.Product

interface ProductClickListener {
    fun onClickProduct(product: Product)
    fun onClickProductPlus(product: Product)
}
