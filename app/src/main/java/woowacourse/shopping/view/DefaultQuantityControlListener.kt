package woowacourse.shopping.view

import woowacourse.shopping.view.product.OnQuantityControlListener

class DefaultQuantityControlListener(
    private val onPlus: (Long) -> Unit,
    private val onMinus: (Long) -> Unit,
) : OnQuantityControlListener {
    override fun onQuantityControlPlusClick(productId: Long) {
        onPlus(productId)
    }

    override fun onQuantityControlMinusClick(productId: Long) {
        onMinus(productId)
    }
}
