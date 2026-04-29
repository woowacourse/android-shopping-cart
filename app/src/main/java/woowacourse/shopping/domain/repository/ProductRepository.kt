package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.product.ProductItems

interface ProductRepository {
    fun getProducts(): ProductItems
}
