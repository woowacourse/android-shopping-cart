package woowacourse.shopping.ui.cart.viewHolder

import woowacourse.shopping.utils.CustomViewOnClickListener

interface OnCartClickListener : CustomViewOnClickListener {
    fun onClick(id: Long)
    fun onRemove(id: Long)
    fun onCheckChanged(id: Long, isChecked: Boolean)
}
