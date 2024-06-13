package woowacourse.shopping.presentation.base

import woowacourse.shopping.presentation.shopping.product.ShoppingUiModel

interface CountHandler {
    fun onPlus(cartProduct: ShoppingUiModel.Product)

    fun onMinus(cartProduct: ShoppingUiModel.Product)
}
