package woowacourse.shopping.cart

import android.widget.TextView
import woowacourse.shopping.uimodel.CartProductUIModel

interface ItemClickListener {
    fun clickDeleteButton(cartProduct: CartProductUIModel, position: Int)
    fun onMinusClick(cartProduct: CartProductUIModel, count: Int, countView: TextView)
    fun onPlusClick(cartProduct: CartProductUIModel, count: Int, countView: TextView)
}
