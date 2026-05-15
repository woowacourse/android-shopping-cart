package woowacourse.shopping.ui.common.model

import woowacourse.shopping.model.Product

data class ProductUiModel(
    val product: Product,
    val cartQuantity: Int = 0,
) {
    val isAddedToCart: Boolean
        get() = cartQuantity > 0
}