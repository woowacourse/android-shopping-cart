package woowacourse.shopping.ui.utils

import woowacourse.shopping.model.Quantity

class AddCartQuantityBundle(
    val productId: Long,
    val quantity: Quantity,
    val onIncreaseProductQuantity: OnIncreaseProductQuantity,
    val onDecreaseProductQuantity: OnDecreaseProductQuantity,
)

typealias OnIncreaseProductQuantity = (productId: Long) -> Unit

typealias OnDecreaseProductQuantity = (productId: Long) -> Unit
