package woowacourse.shopping.view.main.adapter

interface ProductsAdapterEventHandler {
    fun onSelectProduct(productId: Long)

    fun onLoadMoreItems()
}
