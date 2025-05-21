package woowacourse.shopping.presentation.ui.layout

interface QuantityChangeListener {
    fun increaseQuantity(productId: Long)

    fun decreaseQuantity(productId: Long)
}
