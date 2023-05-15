package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.BasketProduct
import woowacourse.shopping.domain.Product

typealias DomainBasketRepository = BasketRepository

interface BasketRepository {
    fun getPartially(size: Int, lastId: Int, isNext: Boolean): List<BasketProduct>
    fun add(product: Product)
    fun remove(basketProduct: BasketProduct)
}
