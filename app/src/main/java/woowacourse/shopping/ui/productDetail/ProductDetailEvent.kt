package woowacourse.shopping.ui.productDetail

sealed class ProductDetailEvent {
    data class NavigateToProductDetail(val productId: Long) : ProductDetailEvent()

    data object NavigateToProductList : ProductDetailEvent()

}
