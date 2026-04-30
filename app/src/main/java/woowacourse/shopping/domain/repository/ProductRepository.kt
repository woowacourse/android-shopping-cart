package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.product.Product
import woowacourse.shopping.domain.model.product.ProductItems

interface ProductRepository {
    fun getProducts(page: Int, pageSize: Int = 20): ProductItems

    fun getProduct(id: String): Product
}
