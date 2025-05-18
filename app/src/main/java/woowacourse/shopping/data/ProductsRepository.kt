package woowacourse.shopping.data

import woowacourse.shopping.domain.Product

interface ProductsRepository {
    fun getAll(): List<Product>
}
