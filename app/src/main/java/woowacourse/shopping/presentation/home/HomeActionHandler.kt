package woowacourse.shopping.presentation.home

interface HomeActionHandler {
    fun onLoadClick()

    fun onMoveToCart()

    fun onProductItemClick(id: Long)

    fun onAddCartItem(id: Long)
}
