package woowacourse.shopping.util.inject

import woowacourse.shopping.data.database.dao.cart.CartDao
import woowacourse.shopping.data.database.dao.recentproduct.RecentProductDao
import woowacourse.shopping.data.datasource.cart.CartDataSource
import woowacourse.shopping.data.datasource.cart.LocalCartDataSource
import woowacourse.shopping.data.datasource.recentproduct.LocalRecentProductDataSource
import woowacourse.shopping.data.datasource.recentproduct.RecentProductDataSource

fun inject(dao: RecentProductDao): RecentProductDataSource.Local =
    LocalRecentProductDataSource(dao)

fun inject(dao: CartDao): CartDataSource.Local =
    LocalCartDataSource(dao)
