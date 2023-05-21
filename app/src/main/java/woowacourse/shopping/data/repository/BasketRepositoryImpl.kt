package woowacourse.shopping.data.repository

import woowacourse.shopping.data.datasource.basket.BasketDataSource
import woowacourse.shopping.data.mapper.toData
import woowacourse.shopping.data.mapper.toDomain
import woowacourse.shopping.domain.BasketProduct
import woowacourse.shopping.domain.repository.BasketRepository

class BasketRepositoryImpl(private val localBasketDataSource: BasketDataSource.Local) :
    BasketRepository {
    override fun getPreviousPartially(
        size: Int,
        standard: Int,
        includeStandard: Boolean
    ): List<BasketProduct> =
        localBasketDataSource.getPreviousPartially(size, standard, includeStandard)
            .map { it.toDomain() }

    override fun getNextPartially(
        size: Int,
        standard: Int,
        includeStandard: Boolean
    ): List<BasketProduct> =
        localBasketDataSource.getNextPartially(size, standard, includeStandard)
            .map { it.toDomain() }

    override fun getAll(): List<BasketProduct> =
        localBasketDataSource.getAll().map { it.toDomain() }

    override fun getByProductId(productId: Int): BasketProduct? =
        localBasketDataSource.getByProductId(productId)?.toDomain()

    override fun add(basketProduct: BasketProduct) {
        localBasketDataSource.add(basketProduct.toData())
    }

    override fun minus(basketProduct: BasketProduct) {
        localBasketDataSource.minus(basketProduct.toData())
    }

    override fun overWriteUpdate(basketProduct: BasketProduct) {
        localBasketDataSource.overWriteUpdate(basketProduct.toData())
    }

    override fun remove(basketProduct: BasketProduct) {
        localBasketDataSource.remove(basketProduct.toData())
    }
}
