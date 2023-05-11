package woowacourse.shopping.data.repository

import woowacourse.shopping.data.datasource.basket.BasketDataSource
import woowacourse.shopping.data.mapper.toData
import woowacourse.shopping.data.mapper.toDomain
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.repository.DomainBasketRepository

class BasketRepository(private val localBasketDataSource: BasketDataSource.Local) :
    DomainBasketRepository {
    override fun getPartially(size: Int): List<Product> =
        localBasketDataSource.getPartially(size).map { it.toDomain() }

    override fun add(product: Product) {
        localBasketDataSource.add(product.toData())
    }

    override fun remove(product: Product) {
        localBasketDataSource.remove(product.toData())
    }
}
