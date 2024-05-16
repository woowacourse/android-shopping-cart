package woowacourse.shopping.presentation.ui.cart

interface CartClickListener {
    fun onItemClick(productId: Long)

    fun onDeleteItemClick(itemId: Long)
}
