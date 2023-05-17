package woowacourse.shopping.ui.shopping.productAdapter

interface ProductsListener {
    fun onClickItem(productId: Int)
    fun onReadMoreClick()
    fun onAddCartOrUpdateCount(productId: Int, count: Int)
}
