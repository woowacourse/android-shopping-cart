package woowacourse.shopping.presentation.model

import woowacourse.shopping.presentation.ui.home.adapter.HomeViewType

sealed interface HomeData {
    val viewType: HomeViewType
}

data class ProductUiModel(
    override val viewType: HomeViewType = HomeViewType.PRODUCT,
    val id: Long,
    val itemImage: String,
    val name: String,
    val price: Int,
) : HomeData

data class RecentlyViewed(
    override val viewType: HomeViewType = HomeViewType.RECENTLY_VIEWED,
    val recentlyViewedProducts: List<RecentlyViewedProduct>,
) : HomeData

data class ShowMoreItem(
    override val viewType: HomeViewType = HomeViewType.SHOW_MORE,
) : HomeData
