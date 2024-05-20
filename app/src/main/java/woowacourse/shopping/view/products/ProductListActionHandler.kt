package woowacourse.shopping.view.products

interface ProductListActionHandler {
    fun onProductItemClicked(productId: Long)

    fun onShoppingCartButtonClicked()

    fun onMoreButtonClicked()
}
