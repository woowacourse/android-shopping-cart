package woowacourse.shopping.presentation.util

import woowacourse.shopping.presentation.model.ShoppingCartItemUiModel

interface QuantityClickListener {
    fun increase(item: ShoppingCartItemUiModel)

    fun decrease(item: ShoppingCartItemUiModel)
}