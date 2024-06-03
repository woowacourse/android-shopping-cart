package woowacourse.shopping.ui.productList.event

sealed class ProductListEvent {
    data class NavigateToProductDetail(val productId: Long) : ProductListEvent()

    data object NavigateToShoppingCart : ProductListEvent()
}
