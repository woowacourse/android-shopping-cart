package woowacourse.shopping.ui.utils

import woowacourse.shopping.model.Product

class AddCartQuantityBundle(
    val product: Product,
    val onIncreaseProductQuantity: OnIncreaseProductQuantity,
    val onDecreaseProductQuantity: OnDecreaseProductQuantity,
)

typealias OnIncreaseProductQuantity = (product: Product) -> Unit

typealias OnDecreaseProductQuantity = (product: Product) -> Unit
