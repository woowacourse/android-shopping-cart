package woowacourse.shopping.product.catalog

import woowacourse.shopping.cart.ButtonEvent

interface QuantityControlListener {
    fun onQuantityChanged(
        buttonEvent: ButtonEvent,
        product: ProductUiModel,
    )

    fun onAdd(product: ProductUiModel)
}
