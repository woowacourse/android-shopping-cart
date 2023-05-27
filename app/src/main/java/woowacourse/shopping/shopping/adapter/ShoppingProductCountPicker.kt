package woowacourse.shopping.shopping.adapter

import woowacourse.shopping.model.CartProductUiModel

interface ShoppingProductCountPicker {

    fun onPlus(product: CartProductUiModel)

    fun onMinus(product: CartProductUiModel)

    fun onAdded(product: CartProductUiModel)
}
