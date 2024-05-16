package woowacourse.shopping.view.products

interface OnClickProducts {
    fun clickProductItem(productId: Long)

    fun clickShoppingCart()

    fun clickLoadPagingData()
}
