package woowacourse.shopping.data.datasource

import woowacourse.shopping.data.dto.response.ProductResponseDto

interface ProductDataSource {
    fun getProducts(): List<ProductResponseDto>

    fun getProduct(id: String): ProductResponseDto
}

