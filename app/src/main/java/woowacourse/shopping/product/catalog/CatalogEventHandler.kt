package woowacourse.shopping.product.catalog

interface CatalogEventHandler {
    fun onProductClick(product: ProductUiModel)

    fun onLoadButtonClick()

    fun onOpenProductQuantitySelector(product: ProductUiModel)

    fun isProductInCart(product: ProductUiModel): Boolean
}
