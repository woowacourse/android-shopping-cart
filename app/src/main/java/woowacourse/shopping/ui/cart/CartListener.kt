package woowacourse.shopping.ui.cart

interface CartListener {
    fun onClickExitCartItem(productId: Long)

    fun onClickIncreaseQuantity(productId: Long)

    fun onClickDecreaseQuantity(productId: Long)
}
