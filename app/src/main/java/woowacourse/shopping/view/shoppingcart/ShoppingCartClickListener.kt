package woowacourse.shopping.view.shoppingcart

import android.widget.TextView
import woowacourse.shopping.model.uimodel.CartProductUIModel

interface ShoppingCartClickListener {
    fun onClickRemove(cartProductUIModel: CartProductUIModel)
    fun onClickCheckBox(cartProductUIModel: CartProductUIModel)
    fun onClickCountButton(cartProductUIModel: CartProductUIModel, textView: TextView)
}
