package woowacourse.shopping.presentation.cart.adapter

interface CartItemClickListener {
    fun onCartItemAdd(productId: Long)

    fun onCartItemRemove(productId: Long)

    fun onCartItemDelete(cartItemId: Long)
}
