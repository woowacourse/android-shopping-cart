package woowacourse.shopping.view.main.adapter

interface ProductsAdapterEventHandler {
    fun onSelectProduct(productId: Long)

    fun onLoadMoreItems()

    fun showQuantity(productId: Long)

    fun onClickIncrease(productId: Long)

    fun onClickDecrease(productId: Long)
}
