package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.Product

interface ProductsRepository {
    val items: List<Product>
}
