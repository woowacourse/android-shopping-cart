package woowacourse.shopping.data.repository

import woowacourse.shopping.data.datasource.basket.BasketDataSource
import woowacourse.shopping.data.mapper.toData
import woowacourse.shopping.data.mapper.toDomain
import woowacourse.shopping.domain.BasketProduct
import woowacourse.shopping.domain.repository.DomainBasketRepository

class BasketRepository(private val localBasketDataSource: BasketDataSource.Local) :
    DomainBasketRepository {
    override fun getPartially(size: Int, lastId: Int, isNext: Boolean): List<BasketProduct> =
        localBasketDataSource.getPartially(size, lastId, isNext).map { it.toDomain() }

    override fun add(basketProduct: BasketProduct) {
        localBasketDataSource.add(basketProduct.toData())
    }

    override fun remove(basketProduct: BasketProduct) {
        localBasketDataSource.remove(basketProduct.toData())
    }
}
