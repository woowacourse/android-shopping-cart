package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.product.Product
import woowacourse.shopping.domain.model.product.Products

interface ProductRepository {
    suspend fun getProducts(): Products

    suspend fun getPagingProducts(
        page: Int,
        pageSize: Int,
    ): Products

    suspend fun hasNextPage(
        currentPage: Int,
        pageSize: Int,
    ): Boolean

    suspend fun findProductById(productId: Int): Product?
}
