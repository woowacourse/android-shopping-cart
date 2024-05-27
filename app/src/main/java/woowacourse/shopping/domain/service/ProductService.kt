package woowacourse.shopping.domain.service

import woowacourse.shopping.domain.model.Product

interface ProductService {
    fun fetchProductsSize(): Int

    fun findAll(): List<Product>

    fun findProductById(productId: Long): Product?

    fun loadPagingProducts(
        offset: Int,
        pagingSize: Int,
    ): List<Product>
}
