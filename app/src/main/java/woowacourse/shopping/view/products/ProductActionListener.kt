package woowacourse.shopping.view.products

interface ProductActionListener {
    fun clickProductItem(productId: Long)

    fun clickShoppingCart()

    fun showMoreButton(isLoadLastItem: Boolean)
}
