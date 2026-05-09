package woowacourse.shopping.data.repository

import kotlinx.collections.immutable.ImmutableList
import woowacourse.shopping.model.Product

interface ProductRepository {
    suspend fun getProducts(
        offset: Int,
        limit: Int,
    ): ImmutableList<Product>

    suspend fun getProductById(id: String): Product
}
