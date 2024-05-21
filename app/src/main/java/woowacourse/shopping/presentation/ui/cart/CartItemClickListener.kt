package woowacourse.shopping.presentation.ui.cart

interface CartItemClickListener {
    fun onCartItemClick(productId: Long)

    fun onDeleteButtonClick(itemId: Long)
}
