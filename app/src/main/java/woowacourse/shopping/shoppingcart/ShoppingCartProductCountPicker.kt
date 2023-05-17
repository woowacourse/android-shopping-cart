package woowacourse.shopping.shoppingcart

import woowacourse.shopping.model.ShoppingCartProductUiModel

interface ShoppingCartProductCountPicker {

    fun onPlus(product: ShoppingCartProductUiModel)

    fun onMinus(product: ShoppingCartProductUiModel)
}
