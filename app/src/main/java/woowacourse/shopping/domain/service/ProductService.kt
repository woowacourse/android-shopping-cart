package woowacourse.shopping.domain.service

import woowacourse.shopping.domain.model.Product

interface ProductService {
    fun findAll(): List<Product>

    fun findProductById(productId: Long): Product?

    fun findPagingProducts(
        offset: Int,
        pagingSize: Int,
    ): List<Product>
}
