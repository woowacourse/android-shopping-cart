package woowacourse.shopping.ui.fashionlist

import woowacourse.shopping.domain.product.CartItem
import woowacourse.shopping.domain.product.Product

sealed class ProductListViewType {
    data class FashionProductItem(
        val product: Product,
        val cartItem: CartItem? = null,
        val isButtonVisible: Boolean = true,
    ) : ProductListViewType()

    data class RecentProducts(val products: List<Product>) : ProductListViewType()

    data object LoadMore : ProductListViewType()
}
