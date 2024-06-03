package woowacourse.shopping.ui.productList

sealed class ProductListNavigationEvent {
    data class ProductDetail(val productId: Long) : ProductListNavigationEvent()

    data object ShoppingCart : ProductListNavigationEvent()
}
