package woowacourse.shopping.presentation.model

import woowacourse.shopping.domain.model.ProductInCart
import woowacourse.shopping.domain.model.RecentlyViewedProduct
import woowacourse.shopping.presentation.ui.home.adapter.HomeViewType

sealed interface HomeData {
    val viewType: HomeViewType
}

data class ProductItem(val productInCart: ProductInCart) : HomeData {
    override val viewType: HomeViewType = HomeViewType.PRODUCT
    val id: Long get() = productInCart.product.id
    val itemImage: String get() = productInCart.product.itemImage
    val name: String get() = productInCart.product.name
    val price: Int get() = productInCart.product.price
    val quantity: Int get() = productInCart.quantity
}

data class RecentlyViewedItem(val recentlyViewedProducts: List<RecentlyViewedProduct>) : HomeData {
    override val viewType: HomeViewType = HomeViewType.RECENTLY_VIEWED
}

data class ShowMoreItem(
    override val viewType: HomeViewType = HomeViewType.SHOW_MORE,
) : HomeData
