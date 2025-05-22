package woowacourse.shopping.product.catalog

interface ProductActionListener {
    fun onProductClick(product: ProductUiModel)

    fun onLoadButtonClick()

    fun onQuantityAddClick(product: ProductUiModel)

    fun onQuantityControlClick(
        event: Int,
        product: ProductUiModel,
    )
}
