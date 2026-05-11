package woowacourse.shopping.data.remote

import woowacourse.shopping.data.remote.dto.ProductDto
import woowacourse.shopping.data.remote.dto.ProductsResponseDto

interface ProductRemoteDataSource {
    suspend fun getProducts(
        page: Int,
        pageSize: Int,
    ): ProductsResponseDto

    suspend fun getProductById(id: String): ProductDto

    suspend fun getProductsByIds(ids: List<String>): List<ProductDto>
}
