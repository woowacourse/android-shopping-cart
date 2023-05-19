package woowacourse.shopping.presentation.ui.shoppingCart.adapter

import woowacourse.shopping.domain.model.Operator

interface ShoppingCartClickListener {
    fun clickItem(position: Int)
    fun clickDelete(position: Int)
    fun clickChangeQuality(position: Int, op: Operator)
    fun checkItem(position: Int, isChecked: Boolean)
}
