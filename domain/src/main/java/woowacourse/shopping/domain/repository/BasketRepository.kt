package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.BasketProduct

interface BasketRepository {

    fun getAll(): List<BasketProduct>

    fun getByProductId(productId: Int): BasketProduct?

    fun add(basketProduct: BasketProduct)

    fun minus(basketProduct: BasketProduct)

    fun update(basketProduct: BasketProduct)

    fun remove(basketProduct: BasketProduct)
}
