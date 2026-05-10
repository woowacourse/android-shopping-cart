package woowacourse.shopping.data.remote.datasource

import woowacourse.shopping.data.remote.dto.ProductResponse

interface ProductRemoteDataSource {
    suspend fun getProducts(): List<ProductResponse>

    suspend fun getProduct(productId: Int): ProductResponse
}
