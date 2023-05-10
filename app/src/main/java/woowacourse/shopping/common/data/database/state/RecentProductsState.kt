package woowacourse.shopping.common.data.database.state

import woowacourse.shopping.domain.RecentProducts

object RecentProductsState : State<RecentProducts> {
    private var recentProducts: RecentProducts = RecentProducts(emptyList())

    override fun save(t: RecentProducts) {
        recentProducts = t
    }

    override fun load(): RecentProducts {
        return recentProducts
    }
}
