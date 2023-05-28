package woowacourse.shopping.feature.cart

interface CartProductClickListener {
    fun onDeleteClick(cartId: Long)
    fun onCartCountChanged(cartId: Long, count: Int)
    fun onSelectedPurchaseChanged(cartId: Long, checked: Boolean)
}
