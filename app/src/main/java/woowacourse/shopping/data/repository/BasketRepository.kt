package woowacourse.shopping.data.repository

import woowacourse.shopping.data.datasource.basket.BasketDataSource
import woowacourse.shopping.data.mapper.toData
import woowacourse.shopping.data.mapper.toDomain
import woowacourse.shopping.domain.BasketProduct
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.repository.DomainBasketRepository

class BasketRepository(private val localBasketDataSource: BasketDataSource.Local) :
    DomainBasketRepository {
    override fun getPartially(
        size: Int,
        standard: Int,
        isNext: Boolean,
        includeStandard: Boolean
    ): List<BasketProduct> =
        localBasketDataSource.getPartially(size, standard, isNext, includeStandard).map { it.toDomain() }

    override fun add(basketProduct: Product) {
        localBasketDataSource.add(basketProduct.toData())
    }

    override fun remove(basketProduct: BasketProduct) {
        localBasketDataSource.remove(basketProduct.toData())
    }
}
