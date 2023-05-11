package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.Product

typealias DomainProductRepository = ProductRepository

interface ProductRepository {
    fun getPartially(size: Int): List<Product>
}
