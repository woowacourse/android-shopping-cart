package woowacourse.shopping.ui.cart.listener

import woowacourse.shopping.model.UiProduct

interface CartClickListener {
    fun onCountChanged(product: UiProduct, count: Int, isIncreased: Boolean)
    fun onCheckStateChanged(product: UiProduct, isChecked: Boolean)
    fun onDeleteClick(product: UiProduct)
}
