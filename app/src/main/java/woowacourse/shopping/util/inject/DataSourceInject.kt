package woowacourse.shopping.util.inject

import woowacourse.shopping.data.database.dao.basket.BasketDao
import woowacourse.shopping.data.database.dao.recentproduct.RecentProductDao
import woowacourse.shopping.data.datasource.basket.BasketDataSource
import woowacourse.shopping.data.datasource.basket.LocalBasketDataSource
import woowacourse.shopping.data.datasource.recentproduct.LocalRecentProductDataSource
import woowacourse.shopping.data.datasource.recentproduct.RecentProductDataSource

fun inject(dao: RecentProductDao): RecentProductDataSource.Local =
    LocalRecentProductDataSource(dao)

fun inject(dao: BasketDao): BasketDataSource.Local =
    LocalBasketDataSource(dao)
