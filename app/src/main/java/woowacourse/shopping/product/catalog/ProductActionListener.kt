package woowacourse.shopping.product.catalog

interface ProductActionListener {
    fun onProductClick(product: ProductUiModel)

    fun onLoadButtonClick()
}
