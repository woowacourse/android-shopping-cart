package woowacourse.shopping.data.repository

import woowacourse.shopping.domain.Product

interface ProductsRepository {
    fun findAll(): List<Product>
}
