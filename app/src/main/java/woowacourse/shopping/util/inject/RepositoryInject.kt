package woowacourse.shopping.util.inject

import woowacourse.shopping.data.datasource.basket.BasketDataSource
import woowacourse.shopping.data.datasource.product.ProductDataSource
import woowacourse.shopping.data.datasource.recentproduct.RecentProductDataSource
import woowacourse.shopping.data.repository.RecentProductRepository
import woowacourse.shopping.domain.repository.BasketRepository
import woowacourse.shopping.domain.repository.ProductRepository

fun inject(localDataSource: ProductDataSource.Local): ProductRepository =
    woowacourse.shopping.data.repository.ProductRepository(localDataSource)

fun inject(localDataSource: RecentProductDataSource.Local): RecentProductRepository =
    RecentProductRepository(localDataSource)

fun inject(localDataSource: BasketDataSource.Local): BasketRepository =
    woowacourse.shopping.data.repository.BasketRepository(localDataSource)
