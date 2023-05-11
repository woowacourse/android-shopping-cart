package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.Product

typealias DomainBasketRepository = BasketRepository

interface BasketRepository {
    fun getPartially(size: Int): List<Product>
    fun add(product: Product)
    fun remove(product: Product)
}
