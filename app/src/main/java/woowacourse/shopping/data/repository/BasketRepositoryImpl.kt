package woowacourse.shopping.data.repository

import woowacourse.shopping.data.datasource.basket.BasketDataSource
import woowacourse.shopping.data.mapper.toData
import woowacourse.shopping.data.mapper.toDomain
import woowacourse.shopping.domain.PageNumber
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.repository.DomainBasketRepository

class BasketRepositoryImpl(private val localBasketDataSource: BasketDataSource.Local) :
    DomainBasketRepository {
    override fun getPartially(page: PageNumber): List<Product> =
        localBasketDataSource.getPartially(page.toData()).map { it.toDomain() }

    override fun add(product: Product) {
        localBasketDataSource.add(product.toData())
    }

    override fun remove(product: Product) {
        localBasketDataSource.remove(product.toData())
    }
}
