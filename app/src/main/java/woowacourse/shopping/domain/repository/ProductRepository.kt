package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.product.Product
import woowacourse.shopping.domain.model.product.ProductItems

interface ProductRepository {
    fun getProducts(): ProductItems

    fun getProduct(id: String): Product
}
