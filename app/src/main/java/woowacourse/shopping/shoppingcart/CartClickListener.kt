package woowacourse.shopping.shoppingcart

import woowacourse.shopping.model.CartProductUiModel

interface CartClickListener {

    fun onClickRemoveBtn(id: Int)

    fun onClickCheckBox(id: Int, isSelected: Boolean)

    fun onClickCheckAllBtn(products: List<CartProductUiModel>, isSelected: Boolean)
}
