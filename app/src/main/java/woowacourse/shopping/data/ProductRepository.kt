package woowacourse.shopping.data

import woowacourse.shopping.domain.Product

interface ProductRepository {
    fun getAll(): List<Product>
}
