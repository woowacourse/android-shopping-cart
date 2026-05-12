package woowacourse.shopping.data.datasource.product

import woowacourse.shopping.data.remote.dto.ProductResponseDto

interface ProductDataSource {
    suspend fun getProducts():List<ProductResponseDto>
    suspend fun getProduct(id: String): ProductResponseDto?
}
