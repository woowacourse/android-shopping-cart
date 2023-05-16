package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.BasketProduct
import woowacourse.shopping.domain.Product

typealias DomainBasketRepository = BasketRepository

interface BasketRepository {
    fun getPartially(
        size: Int,
        standard: Int,
        isNext: Boolean,
        includeStandard: Boolean
    ): List<BasketProduct>

    fun add(product: Product)
    fun remove(basketProduct: BasketProduct)
}
