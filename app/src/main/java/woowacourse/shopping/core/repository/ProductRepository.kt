package woowacourse.shopping.core.repository

import kotlinx.collections.immutable.ImmutableList
import woowacourse.shopping.core.model.Product

interface ProductRepository {
    suspend fun getProducts(
        offset: Int,
        limit: Int,
    ): ImmutableList<Product>

    suspend fun getProductById(id: String): Product
}
