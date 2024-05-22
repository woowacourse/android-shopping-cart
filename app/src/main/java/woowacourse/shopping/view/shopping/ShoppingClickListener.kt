package woowacourse.shopping.view.shopping

interface ShoppingClickListener {
    fun onProductClick(productId: Long)

    fun onLoadMoreButtonClick()
}
