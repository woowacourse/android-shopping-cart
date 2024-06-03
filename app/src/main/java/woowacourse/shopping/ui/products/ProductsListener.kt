package woowacourse.shopping.ui.products

interface ProductsListener {
    fun onClickProductItem(productId: Long)

    fun onClickRecentProductItem(productId: Long)

    fun onClickIncreaseQuantity(productId: Long)

    fun onClickDecreaseQuantity(productId: Long)
}
