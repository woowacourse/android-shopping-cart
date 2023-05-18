package woowacourse.shopping.presentation.model

import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.presentation.ui.home.adapter.HomeViewType

sealed interface HomeData {
    val viewType: HomeViewType
}

data class ProductItem(val product: Product) : HomeData {
    override val viewType: HomeViewType = HomeViewType.PRODUCT
    val id: Long get() = product.id
    val itemImage: String get() = product.itemImage
    val name: String get() = product.name
    val price: Int get() = product.price
}

data class RecentlyViewedItem(val recentlyViewedProducts: List<RecentlyViewedProduct>) : HomeData {
    override val viewType: HomeViewType = HomeViewType.RECENTLY_VIEWED
}

data class ShowMoreItem(
    override val viewType: HomeViewType = HomeViewType.SHOW_MORE,
) : HomeData
