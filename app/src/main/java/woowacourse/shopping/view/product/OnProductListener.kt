package woowacourse.shopping.view.product

import woowacourse.shopping.domain.Product

interface OnProductListener {
    fun onItemClick(product: Product)

    fun onInitPlusButtonClick(productId: Long)

    fun onQuantityControlPlusClick(productId: Long)

    fun onQuantityControlMinusClick(productId: Long)
}
