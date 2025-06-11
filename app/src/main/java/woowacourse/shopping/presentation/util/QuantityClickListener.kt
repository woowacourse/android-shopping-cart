package woowacourse.shopping.presentation.util

import woowacourse.shopping.domain.model.shoppingcart.ShoppingCartItem

interface QuantityClickListener {
    fun increase(item: ShoppingCartItem)

    fun decrease(item: ShoppingCartItem)
}
