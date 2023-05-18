package woowacourse.shopping.shoppingcart

import woowacourse.shopping.model.CartProductUiModel

interface CartProductCountPickerListener {

    fun onPlus(product: CartProductUiModel)

    fun onMinus(product: CartProductUiModel)
}
