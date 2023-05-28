package woowacourse.shopping.productcatalogue

import android.widget.TextView
import woowacourse.shopping.uimodel.CartProductUIModel

interface ProductCountClickListener {
    fun onDownClicked(cartProduct: CartProductUIModel, countView: TextView)
    fun onUpClicked(cartProduct: CartProductUIModel, countView: TextView)
}
