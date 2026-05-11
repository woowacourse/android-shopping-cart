package woowacourse.shopping.data.source

import woowacourse.shopping.data.source.remote.model.ProductResponse

interface ProductDataSource {
    suspend fun getProducts(
        startIndex: Int,
        count: Int,
    ): List<ProductResponse>

    suspend fun getProductById(id: String): ProductResponse

    suspend fun getTotalCount(): Int
}
