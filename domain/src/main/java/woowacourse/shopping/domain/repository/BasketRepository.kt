package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.BasketProduct

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

    fun getAll(): List<BasketProduct>

    fun getByProductId(productId: Int): BasketProduct?

    fun add(basketProduct: BasketProduct)

    fun minus(basketProduct: BasketProduct)

    fun overWriteUpdate(basketProduct: BasketProduct)

    fun remove(basketProduct: BasketProduct)
}
