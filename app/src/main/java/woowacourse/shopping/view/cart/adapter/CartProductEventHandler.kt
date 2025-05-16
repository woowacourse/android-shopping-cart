package woowacourse.shopping.view.cart.adapter

import woowacourse.shopping.domain.ShoppingProduct

interface CartProductEventHandler {
    fun onItemRemoveClick(product: ShoppingProduct)
}
