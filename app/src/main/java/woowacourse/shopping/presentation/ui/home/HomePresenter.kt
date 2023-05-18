package woowacourse.shopping.presentation.ui.home

import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.presentation.model.HomeData
import woowacourse.shopping.presentation.model.HomeMapper.toProductItem
import woowacourse.shopping.presentation.model.HomeMapper.toRecentlyViewedProduct
import woowacourse.shopping.presentation.model.RecentlyViewedItem
import woowacourse.shopping.presentation.model.RecentlyViewedProduct
import woowacourse.shopping.presentation.model.ShowMoreItem
import woowacourse.shopping.presentation.ui.home.adapter.HomeViewType.SHOW_MORE

class HomePresenter(
    private val view: HomeContract.View,
    private val productRepository: ProductRepository,
) : HomeContract.Presenter {
    private var lastProductId: Long = 0
    private val homeData = mutableListOf<HomeData>()
    private var recentlyViewedItem = listOf<RecentlyViewedProduct>()

    override fun setHome() {
        view.setHomeData(homeData)
    }

    override fun fetchRecentlyViewed() {
        recentlyViewedItem =
            productRepository.getRecentlyViewedProducts(UNIT).map { it.toRecentlyViewedProduct() }
        if (recentlyViewedItem.isEmpty()) return
        if (isRecentlyViewedInit()) {
            homeData.add(0, RecentlyViewedItem(recentlyViewedItem))
            view.initRecentlyViewed()
        }
        view.updateRecentlyViewedProducts(recentlyViewedItem.toList())
    }

    override fun fetchProducts() {
        deleteShowMoreItem()
        val start = homeData.size
        val products = productRepository.getProducts(UNIT, lastProductId).map { it.toProductItem() }
        lastProductId = products.lastOrNull()?.id ?: lastProductId
        homeData.addAll(products)
        val isLast = productRepository.isLastProduct(lastProductId)
        if (!isLast) addShowMoreItem()
        view.appendProductItems(start, products.size)
    }

    private fun isRecentlyViewedInit(): Boolean =
        homeData.isEmpty() || (homeData[0] !is RecentlyViewedItem)

    private fun addShowMoreItem() {
        homeData.add(ShowMoreItem())
        view.appendShowMoreItem(homeData.size - 1)
    }

    private fun deleteShowMoreItem() {
        if (homeData.isNotEmpty() && homeData.last().viewType == SHOW_MORE) {
            homeData.removeLast()
            view.removeShowMoreItem(homeData.size)
        }
    }

    companion object {
        private const val UNIT = 10
    }
}
