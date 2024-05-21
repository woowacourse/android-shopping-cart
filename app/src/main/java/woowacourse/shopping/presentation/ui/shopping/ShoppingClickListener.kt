package woowacourse.shopping.presentation.ui.shopping

interface ShoppingClickListener {
    interface ShoppingItemClickListener {
        fun onProductClick(productId: Long)
    }

    interface ShoppingButtonClickListener {
        fun onLoadMoreButtonClick()
    }
}
