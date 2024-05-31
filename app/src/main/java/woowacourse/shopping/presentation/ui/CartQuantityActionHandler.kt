package woowacourse.shopping.presentation.ui

interface CartQuantityActionHandler {
    fun onPlusButtonClicked(productId: Long)

    fun onMinusButtonClicked(productId: Long)
}
