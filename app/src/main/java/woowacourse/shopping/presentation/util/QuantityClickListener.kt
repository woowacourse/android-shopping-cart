package woowacourse.shopping.presentation.util

import woowacourse.shopping.domain.model.ShoppingCartItem

interface QuantityClickListener {
    fun increase(item: ShoppingCartItem)

    fun decrease(item: ShoppingCartItem)
}