package woowacourse.shopping.presentation.ui.layout

interface QuantitySelectorListener {
    fun increaseQuantity(
        productId: Long,
        position: Int,
    )

    fun decreaseQuantity(
        productId: Long,
        position: Int,
    )
}
