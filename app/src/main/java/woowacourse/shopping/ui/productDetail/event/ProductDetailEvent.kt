package woowacourse.shopping.ui.productDetail.event

sealed class ProductDetailEvent {
    data class NavigateToProductDetail(val productId: Long) : ProductDetailEvent()

    data object PopBackStack : ProductDetailEvent()

    data object AddProductToCart : ProductDetailEvent()
}
