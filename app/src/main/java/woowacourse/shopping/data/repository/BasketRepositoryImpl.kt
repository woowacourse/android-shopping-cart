package woowacourse.shopping.data.repository

import woowacourse.shopping.data.datasource.basket.BasketDataSource
import woowacourse.shopping.data.mapper.toData
import woowacourse.shopping.data.mapper.toDomain
import woowacourse.shopping.domain.Basket
import woowacourse.shopping.domain.PageNumber
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.repository.DomainBasketRepository

class BasketRepositoryImpl(private val localBasketDataSource: BasketDataSource.Local) :
    DomainBasketRepository {
    override fun getProductByPage(page: PageNumber): Basket =
        localBasketDataSource.getProductByPage(page.toData()).toDomain(page.sizePerPage)

    override fun getProductInBasketByPage(page: PageNumber): Basket =
        localBasketDataSource.getProductInBasketByPage(page.toData()).toDomain(page.sizePerPage)

    override fun plusProductCount(product: Product) {
        localBasketDataSource.plusProductCount(product.toData())
    }

    override fun minusProductCount(product: Product) {
        localBasketDataSource.minusProductCount(product.toData())
    }

    override fun deleteByProductId(productId: Int) {
        localBasketDataSource.deleteByProductId(productId)
    }

    override fun getProductInBasketSize(): Int =
        localBasketDataSource.getProductInBasketSize()
}
