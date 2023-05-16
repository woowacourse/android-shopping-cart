package woowacourse.shopping.util.inject

import woowacourse.shopping.data.datasource.basket.BasketDataSource
import woowacourse.shopping.data.datasource.product.ProductDataSource
import woowacourse.shopping.data.datasource.recentproduct.RecentProductDataSource
import woowacourse.shopping.data.repository.BasketRepositoryImpl
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.data.repository.RecentProductRepositoryImpl
import woowacourse.shopping.domain.repository.BasketRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.RecentProductRepository

fun inject(localDataSource: ProductDataSource.Local): ProductRepository =
    ProductRepositoryImpl(localDataSource)

fun inject(localDataSource: RecentProductDataSource.Local): RecentProductRepository =
    RecentProductRepositoryImpl(localDataSource)

fun inject(localDataSource: BasketDataSource.Local): BasketRepository =
    BasketRepositoryImpl(localDataSource)
