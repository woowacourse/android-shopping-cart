package woowacourse.shopping.ui.productDetail.event

sealed class ProductDetailError {
    data object LoadProduct : ProductDetailError()

    data object LoadLatestProduct : ProductDetailError()

    data object SaveProductInHistory : ProductDetailError()

    data object AddProductToCart : ProductDetailError()
}
