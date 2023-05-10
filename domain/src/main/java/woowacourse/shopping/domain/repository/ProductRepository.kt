package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.Product

typealias DomainProductRepository = ProductRepository

interface ProductRepository {
    fun getAll(): List<Product>
}
