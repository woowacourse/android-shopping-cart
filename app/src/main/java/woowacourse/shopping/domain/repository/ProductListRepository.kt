package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.Product

interface ProductListRepository {
    fun getProductList(): List<Product>

    fun findProductById(id: Int): Result<Product>
}
