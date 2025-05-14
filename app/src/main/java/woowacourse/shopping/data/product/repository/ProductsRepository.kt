package woowacourse.shopping.data.product.repository

import woowacourse.shopping.domain.product.Product

interface ProductsRepository {
    val products: List<Product>
}
