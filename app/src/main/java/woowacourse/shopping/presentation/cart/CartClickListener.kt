package woowacourse.shopping.presentation.cart

interface CartClickListener {
    fun onItemClick(productId: Long)

    fun onDeleteItemClick(itemId: Long)
}
