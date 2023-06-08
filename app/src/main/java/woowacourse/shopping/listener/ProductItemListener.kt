package woowacourse.shopping.listener

import woowacourse.shopping.ui.products.uistate.ProductUIState

interface ProductItemListener : CountListener {
    fun onItemClick(productId: Long)
    fun onStartCountButtonClick(product: ProductUIState)
    override fun onPlusCountButtonClick(productId: Long, oldCount: Int)
    override fun onMinusCountButtonClick(productId: Long, oldCount: Int)
}
