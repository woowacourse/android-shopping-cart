package woowacourse.shopping.ui.productDetail.event

sealed class ProductDetailEvent {
    data class NavigateToProductDetail(val productId: Long) : ProductDetailEvent()

    data object NavigateToProductList : ProductDetailEvent()

    data object AddProductToCart : ProductDetailEvent()
}
