package woowacourse.shopping.view.cart

interface CartItemClickListener {
    fun onCartItemClick(productId: Long)

    fun onDeleteButtonClick(itemId: Long)

    fun onBackButtonClick()
}
