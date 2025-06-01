package woowacourse.shopping.presentation.cart

import woowacourse.shopping.domain.model.CartItem

interface CartPageClickListener {
    fun onClickPrevious()

    fun onClickNext()

    fun onClickDelete(cartItem: CartItem)
}
