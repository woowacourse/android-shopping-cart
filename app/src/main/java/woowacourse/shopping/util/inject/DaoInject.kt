package woowacourse.shopping.util.inject

import woowacourse.shopping.data.database.ShoppingDatabase
import woowacourse.shopping.data.database.dao.basket.BasketDao
import woowacourse.shopping.data.database.dao.basket.BasketDaoImpl
import woowacourse.shopping.data.database.dao.product.ProductDao
import woowacourse.shopping.data.database.dao.product.ProductDaoImpl
import woowacourse.shopping.data.database.dao.recentproduct.RecentProductDao
import woowacourse.shopping.data.database.dao.recentproduct.RecentProductDaoImpl

fun injectProductDao(database: ShoppingDatabase): ProductDao =
    ProductDaoImpl(database)

fun injectRecentProductDao(database: ShoppingDatabase): RecentProductDao =
    RecentProductDaoImpl(database)

fun injectBasketDao(database: ShoppingDatabase): BasketDao =
    BasketDaoImpl(database)
