package woowacourse.shopping.ui.productlist

import woowacourse.shopping.ui.common.QuantityChangeListener

interface ProductClickListener : QuantityChangeListener {
    fun onClickProduct(productId: Long)
    fun onClickAddButton(productId: Long)
}
