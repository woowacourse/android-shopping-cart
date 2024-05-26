package woowacourse.shopping.presentation.navigation

interface ShoppingNavigator {
    fun navigateToProductDetail(productId: Long)

    fun navigateToCart()

    fun navigateToProductList(flag: Int)

    fun popBackStack()
}
