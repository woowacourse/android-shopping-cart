package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.BasketProduct

typealias DomainBasketRepository = BasketRepository

interface BasketRepository {
    fun getPartially(size: Int, lastId: Int, isNext: Boolean): List<BasketProduct>
    fun add(basketProduct: BasketProduct)
    fun remove(basketProduct: BasketProduct)
}
