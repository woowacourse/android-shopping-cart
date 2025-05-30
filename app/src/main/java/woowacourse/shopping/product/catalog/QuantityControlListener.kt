package woowacourse.shopping.product.catalog

import woowacourse.shopping.cart.ButtonEvent

fun interface QuantityControlListener {
    fun onClick(
        buttonEvent: ButtonEvent,
        product: ProductUiModel,
    )
}
