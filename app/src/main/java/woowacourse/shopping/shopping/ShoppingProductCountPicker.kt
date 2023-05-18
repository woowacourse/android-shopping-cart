package woowacourse.shopping.shopping

import woowacourse.shopping.model.ProductUiModel

interface ShoppingProductCountPicker {

    fun onPlus(product: ProductUiModel)

    fun onMinus(product: ProductUiModel)

    fun onAdded(product: ProductUiModel)
}
