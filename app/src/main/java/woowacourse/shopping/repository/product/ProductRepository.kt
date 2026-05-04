package woowacourse.shopping.repository.product

import woowacourse.shopping.domain.product.Product

interface ProductRepository {
    suspend fun getProducts(page: Int, pageSize: Int): List<Product>

    suspend fun getProduct(id: String?): Product
}
