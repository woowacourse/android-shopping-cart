package woowacourse.shopping.domain.repository

import woowacourse.shopping.data.model.Product

interface ProductRepository {
    fun fetchSinglePage(page: Int): List<Product>

    fun fetchProduct(id: Long): Product
}
