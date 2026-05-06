package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.product.Product
import woowacourse.shopping.domain.model.product.Products
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface ProductRepository {
    fun getProducts(): Products

    fun getPagingProducts(
        page: Int,
        pageSize: Int,
    ): Products

    fun hasNextPage(
        currentPage: Int,
        pageSize: Int,
    ): Boolean

    @OptIn(ExperimentalUuidApi::class)
    fun findProductById(productId: Uuid): Product?
}
