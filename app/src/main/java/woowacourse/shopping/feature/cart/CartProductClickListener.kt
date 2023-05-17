package woowacourse.shopping.feature.cart

interface CartProductClickListener {
    fun onDeleteClick(cartId: Long)
    fun onCartCountChanged(productId: Long, count: Int)
    fun onSelectedPurchaseChanged(productId: Long, checked: Boolean)
}