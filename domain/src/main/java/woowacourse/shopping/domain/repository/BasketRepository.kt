package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.BasketProduct
import woowacourse.shopping.domain.Product

interface BasketRepository {

    fun getPreviousPartially(
        size: Int,
        standard: Int,
        includeStandard: Boolean
    ): List<BasketProduct>

    fun getNextPartially(
        size: Int,
        standard: Int,
        includeStandard: Boolean
    ): List<BasketProduct>

    fun add(product: Product)
    fun remove(basketProduct: BasketProduct)
}
