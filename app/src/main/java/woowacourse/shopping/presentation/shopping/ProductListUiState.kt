package woowacourse.shopping.presentation.shopping

import woowacourse.shopping.domain.model.cart.Cart
import woowacourse.shopping.domain.model.product.Products
import woowacourse.shopping.domain.model.product.RecentlyViewedProducts
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class ProductListUiState(
    val products: Products = Products(),
    val cart: Cart = Cart(),
    val recentlyViewedProducts: RecentlyViewedProducts = RecentlyViewedProducts(),
    val currentPageIndex: Int = 0,
    val isLoading: Boolean = false,
) {
    val totalQuantity: Int
        get() = cart.getTotalQuantity()

    @OptIn(ExperimentalUuidApi::class)
    val productQuantities: Map<Uuid, Int>
        get() =
            cart.cartItems.associate {
                it.product.productId to it.quantity
            }
}
