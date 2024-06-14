package woowacourse.shopping.presentation.ui.detail

interface DetailEventHandler {
    fun addCartItem(productId: Long)

    fun moveBack()

    fun onRecentProductClick(productId: Long)
}
