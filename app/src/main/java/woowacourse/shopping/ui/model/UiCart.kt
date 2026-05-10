package woowacourse.shopping.ui.model

import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.domain.Product

data class UiCart(
    val product: Product,
    val quantity: Int,
)

fun CartItem.toUiModel(product: Product): UiCart =
    UiCart(
        product = product,
        quantity = quantity,
    )
