package woowacourse.shopping.presentation.listener

import woowacourse.shopping.presentation.model.CartProductModel

interface CartCounterListener {
    fun onAddClick(cartProductModel: CartProductModel)
    fun onRemoveClick(cartProductModel: CartProductModel)
}
