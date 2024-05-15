package woowacourse.shopping.view.cart

interface OnClickShoppingCart {
    fun clickBack()
    fun clickCartItem(productId: Long)
    fun clickRemoveCartItem(cartItemId: Long)
}
