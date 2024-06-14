package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.Product

interface ProductsRepository {
    val products: List<Product>
}
