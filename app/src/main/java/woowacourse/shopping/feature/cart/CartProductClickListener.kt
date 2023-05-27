package woowacourse.shopping.feature.cart

import woowacourse.shopping.model.CartProductUiModel

interface CartProductClickListener {
    fun onPlusClick(cartProduct: CartProductUiModel, previousCount: Int)
    fun onMinusClick(cartProduct: CartProductUiModel, previousCount: Int)
    fun onCheckClick(cartProduct: CartProductUiModel, isSelected: Boolean)
    fun onDeleteClick(cartProduct: CartProductUiModel)
}
