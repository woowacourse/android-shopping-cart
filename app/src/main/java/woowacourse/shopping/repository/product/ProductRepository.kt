package woowacourse.shopping.repository.product

import woowacourse.shopping.domain.product.Product

interface ProductRepository {
    fun getProducts(): List<Product>
}
