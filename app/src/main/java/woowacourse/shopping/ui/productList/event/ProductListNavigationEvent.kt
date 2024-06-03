package woowacourse.shopping.ui.productList.event

sealed class ProductListNavigationEvent {
    data class ProductDetail(val productId: Long) : ProductListNavigationEvent()

    data object ShoppingCart : ProductListNavigationEvent()
}
