package woowacourse.shopping.ui.utils

import woowacourse.shopping.model.Quantity

class AddCartQuantityBundle(
    val productId: Long,
    val quantity: Quantity,
    val onIncreaseProductQuantity: (productId: Long) -> Unit,
    val onDecreaseProductQuantity: (productId: Long) -> Unit,
)
