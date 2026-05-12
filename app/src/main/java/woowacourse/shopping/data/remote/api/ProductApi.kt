package woowacourse.shopping.data.remote.api

import woowacourse.shopping.data.remote.dto.ProductResponseDto

interface ProductApi {
    suspend fun getProducts(): List<ProductResponseDto>

    suspend fun getProduct(id: String): ProductResponseDto?
}
