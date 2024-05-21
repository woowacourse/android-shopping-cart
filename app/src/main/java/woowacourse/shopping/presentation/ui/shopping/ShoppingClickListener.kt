package woowacourse.shopping.presentation.ui.shopping

interface ShoppingClickListener {
    fun onProductClick(productId: Long)

    fun onLoadMoreButtonClick()
}
